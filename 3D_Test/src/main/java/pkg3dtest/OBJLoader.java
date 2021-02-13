/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dtest;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class OBJLoader {

    public static object loadModle(File f, double xPos, double yPos, double zPos, double xRot, double yRot, double zRot, double scale) throws FileNotFoundException, IOException {

        String fs = f.toString();
        fs = fs.replace('\\', '/');
        BufferedReader reader = new BufferedReader(new FileReader(f));
        BufferedReader mReader = null;

        ArrayList<Vector> v = new ArrayList();
        ArrayList<Vector> vt = new ArrayList();
        ArrayList<Vector> vn = new ArrayList();
        ArrayList<Triangle> t = new ArrayList();
        polygon p = new polygon();
        object o = new object(xPos, yPos, zPos, xRot, yRot, zRot);
        ArrayList<int[][]> faces = new ArrayList<int[][]>();
        Material currentMaterial = new Material();
        String line;
        String mtllib = null;
        String filePath = null;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("mtllib ")) {

                int index = line.indexOf(line.split(" ")[1]);
                mtllib = line.substring(index, line.length()); //gets the name of mtl file from obj

                index = fs.indexOf(fs.split("/")[countOccurences(fs, '/', 0)]);
                filePath = fs.substring(0, index); //gets the file path of obj / mtl

                mReader = new BufferedReader(new FileReader(filePath + mtllib));
            }
            if (line.startsWith("v  ")) {

                double x = Double.valueOf(line.split(" ")[2]) * scale;
                double y = Double.valueOf(line.split(" ")[3]) * scale;
                double z = Double.valueOf(line.split(" ")[4]) * scale;
                v.add(new Vector(x, y, z));

            } else if (line.startsWith("v ")) {

                double x = Double.valueOf(line.split(" ")[1]) * scale;
                double y = Double.valueOf(line.split(" ")[2]) * scale;
                double z = Double.valueOf(line.split(" ")[3]) * scale;
                v.add(new Vector(x, y, z));

            } else if (line.startsWith("vt ")) {

                double x = Double.valueOf(line.split(" ")[1]);
                double y = Double.valueOf(line.split(" ")[2]);
//                double z = Double.valueOf(line.split(" ")[3]);
                double z = 0;
                vt.add(new Vector(x, y, z));

            } else if (line.startsWith("vn ")) {

                double x = Double.valueOf(line.split(" ")[1]);
                double y = Double.valueOf(line.split(" ")[2]);
                double z = Double.valueOf(line.split(" ")[3]);
                vn.add(new Vector(x, y, z));

            } else if (line.startsWith("f ")) {
                String chunkA = line.split(" ")[1];
                String chunkB = line.split(" ")[2];
                String chunkC = line.split(" ")[3];

                int a = Integer.valueOf(chunkA.split("/")[0]);
                int b = Integer.valueOf(chunkB.split("/")[0]);
                int c = Integer.valueOf(chunkC.split("/")[0]); //all vectors 

//                int at = Integer.valueOf(chunkA.split("/")[1]);
 //               int bt = Integer.valueOf(chunkB.split("/")[1]);
  //              int ct = Integer.valueOf(chunkC.split("/")[1]); //all texture cords

//                int an = Integer.valueOf(chunkA.split("/")[1]);
 //               int bn = Integer.valueOf(chunkB.split("/")[1]);
  //              int cn = Integer.valueOf(chunkC.split("/")[1]); //all normal vectotrs

                Vector av = v.get(a - 1);
 //               av.addTextureVector(vt.get(at - 1));
 //               if (vn.get(an - 1) != null) {
 //                   av.addNormalVector(vn.get(an - 1));
 //               }

                Vector bv = v.get(b - 1);
//                bv.addTextureVector(vt.get(bt - 1));
//                if (vn.get(bn - 1) != null) {
//                    bv.addNormalVector(vn.get(bn - 1));
//                }

                Vector cv = v.get(c - 1);
 //               cv.addTextureVector(vt.get(ct - 1));
//                if (vn.get(cn - 1) != null) {
//                    cv.addNormalVector(vn.get(cn - 1));
//                }

                p.addTriangle(new Triangle(av, bv, cv, currentMaterial));

            } else if (line.startsWith("g ")) { //checks if a new group is to be made
                if (p.triangles.isEmpty()) {
                    p = new polygon();
                    p.setName(line.split("g "));
                } else {
                    o.addPolygon(p);
                    p = new polygon();
                    p.setName(line.split("g "));
                }

            } else if (line.startsWith("usemtl ")) { // checks if a new material should be used

                String mLine;
                String mat = line.split("usemtl ")[1];

                while ((mLine = mReader.readLine()) != null) {

                    if (line.startsWith("usemtl ") && mLine.startsWith("newmtl ")) {
                        if (mat.equals(mLine.split("newmtl ")[1])) {

                            while ((mLine = mReader.readLine()) != null) {

                                mLine = mLine.trim();
                                if (mLine.startsWith("Kd ")) {
                                    currentMaterial = new Material();
                                    double r = Double.valueOf(mLine.split(" ")[1]);
                                    double g = Double.valueOf(mLine.split(" ")[2]);
                                    double b = Double.valueOf(mLine.split(" ")[3]);// all colours
                                    currentMaterial.Ka = new double[]{r, g, b};
                                    break;
                                }

                            }
                            mReader = new BufferedReader(new FileReader(filePath + mtllib));
                            break;
                        }
                    }

                }
            }
        }
        if (!p.triangles.isEmpty()) { //catches if object was defined first
            o.addPolygon(p);
        }
        return o;

    }

    public static int countOccurences(String string, char searchedChar, int index) {
        if (index >= string.length()) {
            return 0;
        }
        if (string.charAt(index) == searchedChar) {
            return 1 + countOccurences(string, searchedChar, index + 1);
        } else {
            return countOccurences(string, searchedChar, index + 1);
        }
    }
}
