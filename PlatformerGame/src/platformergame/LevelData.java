package platformergame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelData {
    /*  DESIGN INFO: 
        resolutin = 28 columns x 7 layers
        0 = Nothing
        1 = Ground_1
        2 = Ground_2
        3 = Ground_3
        q = Cave_ceiling_1
        w = Cave_ceiling_2
        e = Cave_ceiling_3
        r = Cave_ground_1
        t = Cave_ground_2
        z = Cave_ground_3
        u = Cave_ground_4
        i = Cave_ground_5
        o = Cave_ground_6
        p = Cave_ground_7
        a = Jungle_middle_1
        s = Jungle_middle_2
        d = Jungle_left_1
        f = Jungle_right_1
        g = Jungle_center_1
        * = Tree_1
        - = Tree_2
        C = Collectible
        M = Monkey
    */
    private String[] data;
    public LevelData(String[] data){
        this.data = data;
    }
    public LevelData(String path) throws IOException{
        this.data = LevelFromFile(path);
    }
    public String[] getData() {
        return data;
    }
    public String[] LevelFromFile(String path) throws IOException{
        String[] result = new String[12];
        BufferedReader inputStream = null;
        try{
           inputStream = new BufferedReader(new FileReader(path));
           String line = inputStream.readLine();
           int whileCounter = 0;
           while(line!=null){
               result[whileCounter] = line;
               whileCounter++;
               line = inputStream.readLine();
           }
        } finally{
            inputStream.close();
        }
        return result;
    }
    
    
}
