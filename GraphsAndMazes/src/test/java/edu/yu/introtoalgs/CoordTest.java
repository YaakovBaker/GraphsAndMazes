package edu.yu.introtoalgs;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import edu.yu.introtoalgs.GraphsAndMazes.Coordinate;

public class CoordTest {
    
    @Test
    public void correctAdjsReturned(){
        Coordinate c1 = new Coordinate(2, 0);
        List<Coordinate> adjs = c1.adjCoords();
        Coordinate c2 = new Coordinate(1, 0);
        Coordinate c3 = new Coordinate(3, 0);
        Coordinate c4 = new Coordinate(2, 1);
        assertTrue(adjs.contains(c2));
        assertTrue(adjs.contains(c3));
        assertTrue(adjs.contains(c4));
        for(Coordinate cd : adjs ){
            System.out.println(cd);
        }
    }

    @Test
    public void illegalargs(){
        boolean err = false;
        try{
            Coordinate c1 = new Coordinate(-1, 0);
        }catch(IllegalArgumentException e){
            err = true;
        }
        assertTrue(err);
        err = false;
        try{
            Coordinate c2 = new Coordinate(0, -1);
        }catch(IllegalArgumentException e){
            err = true;
        }
        assertTrue(err);
        err = false;
        try{
            Coordinate c3 = new Coordinate(-1, -1);
        }catch(IllegalArgumentException e){
            err = true;
        }
        assertTrue(err);
    }

    @Test
    public void equalsTest(){
        Coordinate c1 = new Coordinate(2, 0);
        Coordinate c2 = new Coordinate(2, 0);
        Coordinate c3 = new Coordinate(2, 1);
        Coordinate c4 = new Coordinate(1, 0);
        assertTrue(c1.equals(c2));
        assertTrue(c1.equals(c1));
        assertTrue(c2.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
    }

    @Test
    public void hashCodeTest(){
        Coordinate c1 = new Coordinate(2, 0);
        Coordinate c2 = new Coordinate(2, 0);
        Coordinate c3 = new Coordinate(2, 1);

        int hash1 = c1.hashCode();
        int hash2 = c2.hashCode();
        int hash3 = c3.hashCode();

        assertEquals(hash1, hash2);
        assertNotEquals(hash1, hash3);
        assertNotEquals(hash2, hash3);
    }
}
