package com.comicmunchies.ip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.comicmunchies.itf.FrameExtractInterface;
import com.comicmunchies.pojo.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



/**
 * Created by Simon.Edwardsson on 10/19/13.
 */
public class FrameExtractor implements FrameExtractInterface{

    public List<Bitmap> getFrameList(Page page) {
/*        String path = "test.jpg";*/
        Bitmap input = BitmapFactory.decodeFile(page.getPath());
        FrameExtractorImpl fei = new FrameExtractorImpl(new BitMapImage(input));
        fei.process();
        List<Bitmap> result = new ArrayList<Bitmap>();
        for(Panel p : fei.getPanels()) {
            result.add((Bitmap)p.getImage().getSource());
        }
        return result;
    }

    private static class FrameExtractorImpl {
        private List<Panel> panels;
        private Image originalImage;
        /**
         *
         * @param image image representing the current comic page
         */
        public FrameExtractorImpl(Image image) {
            this.originalImage = image;
        }

        /**
         * Gets all the panels in one page.
         * If the page has not been process it will be
         * @return a list of all panels
         */
        public List<Panel> getPanels() {
            if(panels == null)
                process();
            return panels;
        }

        /**
         * Finds all the individual panels.
         */
        public void process() {
            panels         = new ArrayList<Panel>();
            Image image    = preprocess(originalImage, 1);

            // Gets a black and white version of our picture
            // TODO: "white" is the color in the upper top
            // boolean bw[][] = image.toBW(Color.WHITE.getRGB(), 3);
            boolean bw[][] = image.toBW(image.getRGB(0,0),4);

            sharpen(bw);


            // a flood fill, setting all of the background (/= panel) to true
            // the starting point does not have to be the upper corner, maybe some
            // heuristic would be better for that

            boolean bgFF[][] = floodFill(bw, 0,0);

		 /* now we go through every pixel, for each value that is false (inside a panel)
		  * do a flood fill to get the panel.

		 */

            for(int x = 0; x < image.getWidth(); x++) {
                for(int y = 0; y < image.getHeight(); y++) {
                    // if we are inside a panel
                    if(!bgFF[x][y]) {
                        Panel panel = extractPanel(image,bgFF,x,y);
                        // some panels are too small to actually be a panel

                        if(panel != null)
                            System.out.println("Adding a new panel: " + panel);

                        if(panel == null)
                            continue;
                        panels.add(panel);

                    }
                }
            }


            //	 panels.clear();
            //	 panels.add(new Panel(img, 0, 0));
        }


        private boolean[][] sharpen(boolean[][] bw) {
            boolean[][] output = new boolean[bw.length][bw[0].length];
            int w = bw.length - 1;
            int h = bw[0].length - 1;

            for(int x = 1; x < w; x++) {
                for(int y = 1; y < h; y++) {
                    if(bw[x-1][y] && bw[x+1][y] && bw[x][y-1] && bw[x][y+1])
                        output[x][y] = true;
                }
            }
            return output;
        }

        /**
         * Adds a border around the image, to help with case where there are no border.
         * @param image
         * @return
         */
        private Image preprocess(Image image, int thickness) {
            if(thickness < 1)
                throw new IllegalArgumentException("thickness need to be larger than zero");
            int w = image.getWidth();
            int h = image.getHeight();
            int wP = w + 2 * thickness;
            int hP = h + 2 * thickness;
            int bgColor = Color.WHITE;  // TODO: change this

            Image preprocess = ImageFactory.makeImage(wP, hP);

            // copy over all of the original contents first
            for(int x = 0; x < w; x++) {
                for(int y = 0; y < h; y++) {
                    preprocess.setRGB(x+thickness, y+thickness, image.getRGB(x, y));
                }
            }

            // Then add the X border
            for(int x = 0; x < wP; x++) {
                preprocess.setRGB(x, 0, bgColor);
                preprocess.setRGB(x, h + thickness, bgColor);
            }

            // And then the Y border
            for(int y = 0; y < hP; y++) {
                preprocess.setRGB(0, y, bgColor);
                preprocess.setRGB(w+thickness, y, bgColor);
            }

            return preprocess;
        }

        private Panel extractPanel(Image image, boolean[][] bgFF, int startX, int startY) {
            boolean ff[][] = new boolean[image.getWidth()][image.getHeight()];
            boolean panelFF[][] = floodFill(bgFF, ff, bgFF, startX, startY);
            int maxX, maxY;
            int minX, minY;
            maxX = maxY = 0;
            minX = minY = Integer.MAX_VALUE;

            // TODO: we could easily remove this loop and put it into the floodfill...

            for(int x = 0; x < image.getWidth(); x++) {
                for(int y = 0; y < image.getHeight(); y++) {
                    // don't check things outside the panel
                    if(!panelFF[x][y])
                        continue;
                    maxX = Math.max(x, maxX);
                    maxY = Math.max(y, maxY);
                    minX = Math.min(x, minX);
                    minY = Math.min(y, minY);
                }
            }

            int w = maxX - minX;
            int h = maxY - minY;

            //TODO: this threshold should be a parameter
            if(w < 40 || h < 40)
                return null;
            System.out.println("x: " + minX + " <-> " + maxX + " (" + (maxX- minX) + ")");
            System.out.println("y: " + minY + " <-> " + maxY + " (" + (maxY- minY) + ")");
            Image result = ImageFactory.makeImage(w+1,h+1);
            for(int x = 0; x < image.getWidth(); x++) {
                for(int y = 0; y < image.getHeight(); y++) {
                    if(!panelFF[x][y])
                        continue;
                    try {
                        result.setRGB(x - minX, y - minY, image.getRGB(x, y));
                    } catch(Exception e) {
                        System.out.println("x: " + (x-minX));
                        System.out.println("y: " + (y-minY));
                        System.exit(1);
                    }

                }
            }

            return new Panel(result, minX, minY);
        }

        private boolean[][] floodFill(boolean input[][], boolean output[][], boolean mask[][], int startX, int startY) {
            // this is the "color" we need to match against
            boolean matchingState = input[startX][startY];
            int w = input.length;
            int h = input[0].length;

            Stack<Point> s = new Stack<Point>();
            s.add(new Point(startX,startY));

            while(!s.empty()) {
                Point p = s.pop();
                int x = p.getX();
                int y = p.getY();

                // if we match then we update the output
                if(input[x][y] == matchingState) {
                    output[x][y] = true;
                    if(mask != null)
                        mask[x][y] = true;
                } else {
                    continue;
                }

                if(x > 0 && !output[x-1][y])
                    s.add(new Point(x-1,y));
                if(x < w-1 && !output[x+1][y])
                    s.add(new Point(x+1,y));
                if(y > 0 && !output[x][y-1])
                    s.add(new Point(x,y-1));
                if(y < h-1 && !output[x][y+1])
                    s.add(new Point(x,y+1));
            }
            return output;
        }
        private boolean[][] floodFill(boolean input[][], boolean output[][], int startX, int startY) {
            return floodFill(input, output, null, startX, startY);
        }

        private boolean[][] floodFill(boolean input[][], int startX, int startY) {
            boolean ff[][] = new boolean[input.length][input[0].length];
            return floodFill(input,ff,startX, startY);
        }

    }
}
