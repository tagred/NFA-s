/*
**
** Author Taylor Gordon
** 11/24/2019
** CSCE 355 Foundations of Computation Fall 2019 fenner
** Takes the description of an NFA N (with no ε-transitions) and a list of strings as input and output the acceptance/rejection result of running N on each string in the list
** Link to assignment pdf https://cse.sc.edu/~fenner/csce355/prog-proj/programming-assignment.pdf
**
*/
import java.io.*;
import java.util.*;
public class SimulationMain{

    public static States[] NFA;
	public static void main(String[] args){ 
	int num_states = 0;
	int alphabet = 0;   
    LinkedList<String> lines = new LinkedList<String>();
	LinkedList<Integer>  accepting = new LinkedList<Integer>();
	LinkedList<String>  inputs = new LinkedList<String>();
    try{
	    File file = new File(args[0]);
	    Scanner sc = new Scanner(file);
        String line = "";
        int k = 0;
	    int i = 0;
    //TODO make into own seperate filereader class
    	while (sc.hasNextLine()){
            
	    	if(i == 0){
	        	//sets number of states
	        	while(!sc.hasNextInt()){
	            	sc.next();
	        	}
	        	num_states = sc.nextInt();
	    	}
	    	else if (i == 1){
	        	//sets alphabet size
	        	while(!sc.hasNextInt()){
	            	sc.next();
	        	}
	        	alphabet = sc.nextInt();
	    	}
	    	else if(i == 2){
	    	//sets accepting states
            for (int x =0; x<2;x++) 
	        		sc.next();
	    		while(sc.hasNextInt())
	        		accepting.add(sc.nextInt());
	    	}     
            k++;  
            //holds all lines in doc so that nfa transitions can be later stored into array of states
            line = sc.nextLine();
	        i++; 
            lines.add(line);
        }
 
	
	sc.close();
	} catch(Exception e) {
		e.printStackTrace();
	}
    //States stores transitions in a list of sets
    NFA = new States[num_states];
    for (int i=0; i<num_states;i++){
        NFA[i] = new States();
    }
    //first three lines describe nfa
    for(int i = 0; i<3;i++)
    lines.removeFirst();

    String list[] = lines.toArray(new String[lines.size()]);

    //Removes braces add states to NFA
    String delim = "[\\}]+";
    for (int i = 0; i<num_states; i++){
            String[] newList = list[i].split("}");
        for (int j = 0; j <=alphabet;j++){
                Set<Integer> s1 = new HashSet<Integer>();
                newList[j]+="}";
             for (int x = 0; x<num_states; x++){
                if (newList[j].contains(","+Integer.toString(x) + ",") || 
                    newList[j].contains("{" + Integer.toString(x) + "}") || 
                    newList[j].contains("{"+Integer.toString(x) + ",") ||
                    newList[j].contains(","+Integer.toString(x)+"}")){
                     s1.add(x);
                 }
                
             }
           NFA[i].addTransition(s1);
         }

       }  
     Scanner sc = new Scanner(System.in);
        //will take stdin until Ctrl + d pressed
        String inputLine;
        while(sc.hasNextLine()) {
            inputLine = sc.nextLine();
            inputs.add(inputLine);
            
    }
    //outputs results

    for(int i = 0;i<inputs.size();i++){
         if(!accepting.isEmpty()){
             LinkedList<Integer> ascii = toChar(inputs.get(i));
             if(accepting(ascii,accepting)){
                 System.out.println("accept");
             }else{
                 System.out.println("reject");
             }
         }else{
            System.out.println("reject");
        }
        
    }
    
}
        /*
        uses thompsons algorithm to determine wheather accepting or rejection
        takes list of all active states and adds the to list once loops are done checks if any values are accepting in the active states
        */
    public static boolean accepting(LinkedList<Integer> ascii, LinkedList<Integer> accepting){
        Set<Integer> accepted = new HashSet<Integer>();
        Set<Integer> copy = new HashSet<Integer>();
        int currState = 0;

        for (int j = 0;j<ascii.size();j++){
            
            if (j==0){
            for (int x : NFA[currState].getStateAt(ascii.get(j))){
                accepted.add(x);
            }
            } else {
         //   System.out.println("in elese");
             for(int x : copy){
                for(int y : NFA[x].getStateAt(ascii.get(j))){
                   accepted.add(y);
                    }
               }    
                        

            }
            copy.clear();
            copy.addAll(accepted);
            accepted.clear();
          /*  for (int x : copy)
             System.out.print(x);
            System.out.println();*/
              }
            for (int x : copy){
                if (accepting.contains(x)){
                    return true;
            }
        
         }
        if(ascii.size() == 0 && accepting.contains(0)){
        return true;
        }
            
        return false;
    } 
    //converts inputs from stdin into chars and then converts those into matching ascii ints. 
    //96 subtracted so that a = 1 and z = 26
    public static LinkedList<Integer> toChar(String s){
        char[] c = s.toCharArray();
        LinkedList<Integer> ascii = new LinkedList<Integer>();
         for (int i = 0; i<s.length();i++){
            int x = (int)c[i] - 96;
            ascii.add(x);
            }    

        return ascii;
    }
}
