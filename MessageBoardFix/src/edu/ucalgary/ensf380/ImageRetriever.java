//package edu.ucalgary.ensf380;
//
//import java.util.List;
//
//public class ImageRetriever {
//
//    private List<String> imagePaths;
//
//    public ImageRetriever() {
//        RetrieveImage.fetchImagePaths();
//        //this.imagePaths = RetrieveImage.getImagePaths();
//    }
//
//    public List<String> getImagePaths() {
//        return imagePaths;
//    }
//
//    public static void main(String[] args) {
//        ImageRetriever imageRetriever = new ImageRetriever();
//        List<String> imagePaths = imageRetriever.getImagePaths();
//
//        for (String path : imagePaths) {
//            System.out.println("Image Path: " + path);
//        }
//    }
//}