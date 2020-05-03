package model;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.*;


    public class Serializer implements Serializable{
        private static final long serialVersionUID = 35L;
        protected static final String dataExtension=".dat";
        protected static final String albumsFile = "albums.data";
        protected static String dataDir;
        protected String currAlbumFileName;

        public Serializer(String path) {
            try {
                constructorHelper(path);
                System.out.println("success making directory");
            } catch(IOException e) {

            }
        }
        /**
         * creates dat
         * @throws IOException
         */
        public void constructorHelper(String path) throws IOException {
            // Set path for files to be written.
            dataDir = path;
            File isDir = new File(dataDir + File.separator );
            File datDir = new File(dataDir + File.separator + albumsFile);
            if(!isDir.isDirectory()) {
                //System.out.println("making dir");
                if(isDir.mkdir()) {
                    System.out.println("made dir successfully");
                }

            }
        }
        /**
         * writes list of albums to serialize
         * @param albumsList
         * @throws IOException
         */
        public void writeAlbums(ArrayList<Album> albumsList) throws IOException {
            ObjectOutputStream oos;
            try {
                oos= new ObjectOutputStream(new FileOutputStream(dataDir + File.separator + albumsFile));
                System.out.println("created output stream, about to write object");
                oos.writeObject(albumsList);
                System.out.println("wrote object");
                oos.close();
            }catch(IOException e) {
                e.printStackTrace();
            }

            return;
        }
        /**
         * writes one album to serialize
         * @param album
         */
        public void writeAlbum(Album album) {
            System.out.println("in writeAlbum");
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(dataDir + File.separator + album.getAlbumName().concat(dataExtension)));
                System.out.println("created output stream, about to write object");
                oos.writeObject(album);
                System.out.println("wrote object");
                oos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         *
         * @param albumname
         * @return
         */
        public boolean deleteAlbum(String albumname){
            File tempFile=new File(dataDir + File.separator + albumname.concat(dataExtension));
            boolean exists = tempFile.exists();
            if(!exists) {
                return false;
            }
            File file= new File(dataDir + File.separator + albumname.concat(dataExtension));
                if(file.exists()) {
                    file.delete();
                    return true;
                }
            return false;
        }
        /**
         * reads user to obj
         * @param albumname
         * @return
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public Album readAlbum(String albumname)throws IOException, ClassNotFoundException {
            File tempFile=new File(dataDir + File.separator + albumname.concat(dataExtension));
            boolean exists = tempFile.exists();
            if(!exists) {
                System.out.println("User does not exist");
                return null;
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(dataDir + File.separator + albumname.concat(dataExtension)));
                Album newAlbum = (Album)ois.readObject();
                ois.close();
                return newAlbum;
            }catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        /**
         * reads albums list to obj
         * @return
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public ArrayList <Album> readAlbums()throws IOException, ClassNotFoundException {
            System.out.println("in readUsers");
            File tempFile=new File(dataDir + File.separator + albumsFile);

            boolean exists = tempFile.exists();
            if(!exists) {
                ArrayList<Album> newAlbums = new ArrayList<Album>();
                return newAlbums;
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(dataDir + File.separator + albumsFile));
                //System.out.println("pre create");
                ArrayList<Album> newAlbums = (ArrayList<Album>) ois.readObject();
                //System.out.println("post creata");
                ois.close();
                return newAlbums;
            }catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

    }

