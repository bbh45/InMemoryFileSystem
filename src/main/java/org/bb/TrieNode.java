package org.bb;

import java.util.HashMap;
import java.util.Map;

public class TrieNode{
    boolean isFile;
    Map<String, TrieNode> children;
    String content;
    public TrieNode(){
        this.isFile = false;
        this.children = new HashMap<>();
        this.content = "";
    }
}
