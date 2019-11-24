/*
**
** Author Taylor Gordon
** 11/24/2019
** CSCE 355 Foundations of Computation Fall 2019 fenner
** Link to assignment pdf https://cse.sc.edu/~fenner/csce355/prog-proj/programming-assignment.pdf
**
*/
import java.io.*;
import java.util.*;

public class States {
    private List<Set<Integer>> sets = new ArrayList<Set<Integer>>();
    public States() {
	}
    public Set<Integer> getStateAt(int x){
       return sets.get(x);
    }
    public void printSets(){
        System.out.println(sets);
    }
    public void addTransition(Set<Integer> s1){
        sets.add(s1);
    }
    public void Union(int i,Set<Integer> s1 ){
        sets.get(i).addAll(s1);
    }
    public Set<Integer> Intersection(Set<Integer> s1, Set<Integer> s2){
        Set<Integer> inter = new HashSet<Integer>(s1);
        inter.retainAll(s2);
        return inter;
    }
    public Set<Integer> Reorder(Set<Integer> s1){
        TreeSet<Integer> tree = new TreeSet<Integer>(s1);
        return tree;
    }
    public void setEmpty(){
        Set<Integer> s = new HashSet<Integer>();
        sets.set(0,s);
    }
    public boolean hasnext(int i){
    if (sets.get(i).isEmpty()){
        return false;
    }
    return true;
    }
}
