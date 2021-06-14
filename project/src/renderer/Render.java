package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

public class Render {
    ImageWriter imageWriter;
    Camera camera;
    RayTracerBase rayTracerBase;
    boolean depth = false;
    boolean superSampling = false;
    private int threadsNumber = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if (threads != 0) threadsNumber = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            threadsNumber = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     */
    private class Pixel {
        private long maxRows = 0; // Ny
        private long maxCols = 0; // Nx
        private long pixels = 0; // Total number of pixels: Nx*Ny
        public volatile int row = 0; // Last processed row
        public volatile int col = -1; // Last processed column
        private long counter = 0; // Total number of pixels processed
        private int percents = 0; // Percent of pixels processed
        private long nextCounter = 0; // Next amount of processed pixels for percent progress

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            pixels = maxRows * maxCols;
            nextCounter = pixels / 100;
            if (Render.this.print) System.out.printf("\r %02d%%", percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (print && percents > 0) System.out.printf("\r %02d%%", percents);
            if (percents >= 0) return true;
            if (print) System.out.printf("\r %02d%%", 100);
            return false;
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++counter;
            if (col < maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (print && counter == nextCounter) {
                    ++percents;
                    nextCounter = pixels * (percents + 1) / 100;
                    return percents;
                }
                return 0;
            }
            ++row;
            if (row < maxRows) {
                col = 0;
                if (print && counter == nextCounter) {
                    ++percents;
                    nextCounter = pixels * (percents + 1) / 100;
                    return percents;
                }
                return 0;
            }
            return -1;
        }
    }

    public void renderImage() {
        if (imageWriter == null || rayTracerBase.scene == null || camera == null || rayTracerBase == null)
            throw new MissingResourceException("not all the values are okay", "Render", "1");
        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();
        Ray ray = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
        final Pixel thePixel = new Pixel(nY, nX); // Main pixel management object
        Thread[] threads = new Thread[threadsNumber];
        for (int i = threadsNumber - 1; i >= 0; --i) { // create all threads
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel(); // Auxiliary thread’s pixel object
                while (thePixel.nextPixel(pixel)) {
                    if (!depth && !superSampling) {
                        imageWriter.writePixel(pixel.col, pixel.row, rayTracerBase.traceRay(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row)));
                    } else if (depth) {
                        if (rayTracerBase.scene.geometries.box.hasIntersection(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row)))
                            imageWriter.writePixel(pixel.col, pixel.row, rayTracerBase.traceRay(camera.constructRays(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row), true, 0, 0, 0)));
                        else
                            imageWriter.writePixel(pixel.col, pixel.row, rayTracerBase.scene.background);
                    } else {
                        if (rayTracerBase.scene.geometries.box.hasIntersection(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row)))
                            imageWriter.writePixel(pixel.col, pixel.row, rayTracerBase.traceRay(camera.constructRays(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), pixel.col, pixel.row), false, 1, 1, 1000)));
                        else
                            imageWriter.writePixel(pixel.col, pixel.row, rayTracerBase.scene.background);
                    }
                    //List<Ray> rays = camera.constructRays(camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row), false, 1, 1, 1);
                    //imageWriter.writePixel(pixel.col, pixel.row, rayTracerBase.traceRay(rays));
                }
            });
        }
        for (Thread thread : threads) thread.start(); // Start all the threads
        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (print) System.out.printf("\r100%%\n"); // Print 100%
    }

    public void renderImage1() {
        for (int i = 0; i < (imageWriter.getNx()); ++i)
            for (int j = 0; j < imageWriter.getNy(); ++j) {
                if (!depth && !superSampling) {
                    imageWriter.writePixel(j, i, rayTracerBase.traceRay(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i)));
                } else if (depth) {
                    imageWriter.writePixel(j, i, rayTracerBase.traceRay(camera.constructRays(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i), true, 0, 0, 0)));
                } else {
                    imageWriter.writePixel(j, i, rayTracerBase.traceRay(camera.constructRays(camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i), false, 1, 1, 1000)));
                }
            }

    }

    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is null", "Render", "2");
        for (int k = 0; k < imageWriter.getNx(); k += interval)
            for (int t = 0; t < imageWriter.getNy(); ++t)
                imageWriter.writePixel(k, t, color);
        for (int k = 0; k < imageWriter.getNx(); k += interval)
            for (int t = 0; t < imageWriter.getNy(); ++t)
                imageWriter.writePixel(t, k, color);
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is null", "Render", "2");
        imageWriter.writeToImage();
    }

    //region setters
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /*
        public Render setScene(Scene scene) {
            rayTracerBase.scene = scene;
            return this;
        }
    */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    public Render setDepth(Boolean depth) {
        this.depth = depth;
        return this;
    }

    public Render setSuperSampling(Boolean superSampling) {
        this.superSampling = superSampling;
        return this;
    }
    //endregion
}
