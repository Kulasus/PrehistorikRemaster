/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;
    
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LeaderboardsLine implements Serializable, Comparable<LeaderboardsLine>{
    private int score;
    private String name;
    public LeaderboardsLine(int score, String name){
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(LeaderboardsLine l) {
        return this.score - l.score;
    }
    
    @Override
    public String toString(){
        return this.name + " " + this.score; 
    }
    
    public String getName(){
        return name;
    }
    
    


}
