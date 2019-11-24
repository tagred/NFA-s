/*
**
** Author Taylor Gordon
** 11/24/2019
** CSCE 355 Foundations of Computation Fall 2019 fenner
** Take the description of an ε-NFA and output an equivalent NFA without ε-transitions
** Link to assignment pdf https://cse.sc.edu/~fenner/csce355/prog-proj/programming-assignment.pdf
**
*/
import java.io.*;
import java.util.*;
public class EpsilonMain{

	public static void main(String[] args){ 
	int num_states = 0;
	int alphabet = 0;   
    LinkedList<String> lines = new LinkedList<String>();
	Set<Integer>  accepting = new TreeSet<Integer>();
    try{
	    File file = new File(args[0]);
	    Scanner sc = new Scanner(file);
        String line = "";
        int k = 0;
	    int i = 0;
    	while (sc.hasNextLine()){
            
	    	if(i == 0){
	        	//TODO set num of states
	        	//	sc.useDelimiter("\D");
                
	        	while(!sc.hasNextInt()){
	            	sc.next();

	        	}
	        	num_states = sc.nextInt();
	    	}
	    	else if (i == 1){
	        	//TODO set alphabet size
	        	while(!sc.hasNextInt()){
	            	sc.next();
	        	}
	        	alphabet = sc.nextInt();
	    	}
	    	else if(i == 2){
	    	//TODO set accepting states
            for (int x =0; x<2;x++) 
	        		sc.next();
 
	    		while(sc.hasNextInt())
	        		accepting.add(sc.nextInt());
	    	}
            
            k++;  
            
            line = sc.nextLine();
	        i++; 
            lines.add(line);

        }
	sc.close();
	} catch(Exception e) {
		e.printStackTrace();
	}
    States NFA[] = new States[num_states];
    for (int i=0; i<num_states;i++){
        NFA[i] = new States();
    }
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

       }// end of intial state additions
        //adding accepting states
        for (int x = 0; x < num_states;x++){
		    for (int i = 0; i < num_states; i++){
                 Set<Integer> copy= new HashSet<Integer>(NFA[i].getStateAt(0));
                 for(int j : copy){             
			    	    if (accepting.contains(j) && !accepting.contains(i))
                           accepting.add(i);
		            }	
                }   
            }

        //epsilonUnion
        for(int j =0;j<num_states;j++){
            for(int i =0;i<num_states;i++){
                Set<Integer> copy= new HashSet<Integer>(NFA[i].getStateAt(0));
                 for(int x : copy){
                     NFA[i].Union(0,NFA[x].getStateAt(0)); 
                     }            
            }
        }
      for(int i = 0; i<num_states;i++){
            for(int j = 0; j<num_states;j++){
              if (NFA[i].getStateAt(0).contains(j))
                
                for (int k = 1; k<=alphabet;k++){
                    NFA[i].Union(k,NFA[j].getStateAt(k));
                  }  
             }   
      }
        for(int i = 0; i<num_states;i++){
           NFA[i].setEmpty();
        }  
      //print results
      System.out.println("Number of states: " + num_states);
      System.out.println("Alphabet size: " + alphabet);
      System.out.print("Accepting states:");
      Iterator<Integer> it = accepting.iterator(); 
      while(it.hasNext()){
        System.out.print(" " +it.next());
        }
        System.out.println();
        for (int i = 0; i<num_states;i++){            
            for (int j = 0; j<=alphabet;j++){
                 System.out.print("{");
                 Iterator<Integer> itr = NFA[i].Reorder(NFA[i].getStateAt(j)).iterator(); 
                 while(itr.hasNext()){
                     System.out.print(itr.next());
                     if(itr.hasNext()) {System.out.print(",");}
                 }  
                 System.out.print("}");
            }    
          System.out.println();
        }
}
}

