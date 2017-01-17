/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

/**
 *
 * @author Olasubomi
 */
public class ParseArray {
private String[] textArray = null;
private int maxSize = 0;
private int nElems = 0;
private int position = 0;

   public ParseArray(String[] inputArray) {
      textArray = inputArray;
      maxSize = textArray.length;
      nElems = maxSize - 1;
   }
   
   
   public ParseArray(int size) {
       maxSize = size;
       textArray = new String[maxSize];
       nElems = 0;
   }
   
   public void delete(String key) {
   int j = 0;
     for(int x = 0; x < nElems; x++) {
        if(textArray[x].equalsIgnoreCase(key)) {
           for(j = x; j < nElems; j++) {
              textArray[j] = textArray[j+1];
           }
           nElems--;
           x--;
          }
        
     }
                  
   }
   
   public void insert(String key) {
   boolean noDups = false;
      if(nElems == 0) {
         textArray[nElems++] = key;   
      }
      else {
            for(int i = 0; i < nElems; i++) {
                if(key.equalsIgnoreCase(textArray[i])) {
                    noDups = false;
                    break;
                }
                else {
                    noDups = true;
                }
            }   
             if(noDups) {
                textArray[nElems++] = key;
             }
      }
   }
   
   
   public void displayContents() {
       for(int x = 0; x <= nElems; x++) {
          System.out.println(textArray[x]); 
       }
   }
   
   public String getText(int x) {
       return textArray[x];
   }
   
   public int length() {
       return maxSize;
   }
   
   public int getNElems() {
       return nElems;
   }
   
   public boolean isEmpty() {
       return (nElems == 0);
   }
   
   public boolean isFull() {
       return (nElems == maxSize);
   }
}
