package org.bb;
import java.util.*;

public class FileSystem {

    private TrieNode root;
    public FileSystem(){
        this.root = new TrieNode();
    }
    public List<String> ls(String path){
        List<String> result = new ArrayList<>();
        try{
            TrieNode node = getNode(path);
            if(node.isFile == true){
                result.add(path.substring(path.lastIndexOf("/")+1));
            }else{
                result.addAll(node.children.keySet());
                Collections.sort(result);
            }
        }catch (FileNotFoundInPathException ex){
            System.out.println(ex.message);
        }
        return result;
    }

    public TrieNode getNode(String path) throws FileNotFoundInPathException {
        List<String> dir = split(path, '/');
        TrieNode curr = root;
        for(String d: dir){
            if (!root.children.containsKey(d)){
                //System.out.println(d +": No such file or directory");
                throw new FileNotFoundInPathException(d +": No such file or directory");
            }
            curr = root.children.get(d);
        }
        return curr;
    }

    public void mkdir(String path){
        TrieNode node = putNode(path);
        node.isFile = false;
    }

    public void addContentToFile(String path, String content){
        TrieNode node = putNode(path);
        node.isFile = true;
        node.content = content;
    }

    public String readContentFromFile(String path){
        TrieNode node = putNode(path);
        if(!node.isFile){
            System.out.println(path.substring(path.lastIndexOf("/")+1)+": This is not a file");
            return null;
        }
        return node.content;
    }

    public TrieNode putNode(String path){
        TrieNode curr = root;
        for(String d : split(path, '/')){
            if (!curr.children.containsKey(d)){
                curr.children.put(d, new TrieNode());
            }
            curr = curr.children.get(d);
        }
        return curr;
    }

    private List<String> split(String path, char delim) {
        if (path.equals("/")) {
            return new ArrayList<>();
        }
        String[] parts = path.split(String.valueOf(delim));
        return Arrays.asList(parts).subList(1, parts.length);
    }
    /*
          ----a.txt(content of file a)
          |
    root -|-----b----d.txt(content of file d)
          |     |__e
          ----c

     */

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        fileSystem.addContentToFile("/a.txt","content of file a.txt");
        fileSystem.mkdir("/b");
        fileSystem.mkdir("/c");
        System.out.println("ls at path / : "+fileSystem.ls("/"));
        System.out.println("ls at path /b : "+fileSystem.ls("/b"));

        fileSystem.addContentToFile("/b/d.txt","content of file d.txt");
        fileSystem.mkdir("/b/e");
        System.out.println("ls at path /b : "+fileSystem.ls("/b"));

        System.out.println(fileSystem.readContentFromFile("/a.txt"));
        System.out.println(fileSystem.readContentFromFile("/b/d.txt"));
    }
}
