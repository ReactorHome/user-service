package reactor.cloud;

import reactor.models.Face;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;
import java.util.Comparator;

public class FaceUtility {

    private final static double threshold = 0.45;

    public static double[] rawToArray(String raw) {
        raw = raw.substring(1, raw.length()-1);

        String[] rawSplit = raw.split(",");

        return Stream.of(rawSplit).mapToDouble(Double::parseDouble).toArray();

    }

    public static double distanceBetween(double[] arr1, double[] arr2) {
        double sum = 0;
        for(int i = 0; i < arr1.length; i++) {
            sum += Math.pow(arr1[i] - arr2[i], 2);
        }

        return Math.sqrt(sum);
    }

    public static Face findSimilar(double[] arr, List<Face> faces){
        double minDist = -1;
        Face minFace = null;

        for(Face face : faces){
            double faceDist = distanceBetween(arr, rawToArray(face.getFaceData()));
            System.out.println(face.getName() + ": " + faceDist);

            if(faceDist < threshold && (minDist == -1 || faceDist < minDist)){
                minDist = faceDist;
                minFace = face;
            }
        }

        if(minFace == null){
            return null;
        }

        return minFace;
    }

    public static boolean isSafe(String rawData, List<Face> faces){
        PriorityQueue<Face> heap = new PriorityQueue<>(new FaceComparator());
        for(Face face : faces){
            face.setRawData(rawData);
        }
        heap.addAll(faces);

        if(heap.isEmpty()){
            return false;
        }

        int k = (int)Math.sqrt(faces.size());
        k = (k/2)*2 + 1;
        double safeSum = 0;

        Object[] sortedFaces = heap.toArray();
        for(int i = 0; i < k; i++) {
            Face simFace = heap.poll();
            double d = distanceBetween(rawToArray(rawData), rawToArray(simFace.getFaceData()));

            if (simFace.isSafe())
                safeSum += 1 / d;
            else
                safeSum -= 1 / d;
        }
        System.out.println("sum of " + k + " total nearest faces: " + safeSum);
        return safeSum > 0;
    }

    static class FaceComparator implements Comparator<Face>{
        @Override
        public int compare(Face face1, Face face2){
            double d1 = distanceBetween(rawToArray(face1.getFaceData()), rawToArray(face1.getRawData()));
            double d2 = distanceBetween(rawToArray(face2.getFaceData()), rawToArray(face2.getRawData()));

            return Double.compare(d1, d2);
        }
    }
}
