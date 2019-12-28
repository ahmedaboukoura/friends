package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

    /**
     * Finds the shortest chain of people from p1 to p2.
     * Chain is returned as a sequence of names starting with p1,
     * and ending with p2. Each pair (n1,n2) of consecutive names in
     * the returned chain is an edge in the graph.
     * 
     * @param g Graph for which shortest chain is to be found.
     * @param p1 Person with whom the chain originates
     * @param p2 Person at whom the chain terminates
     * @return The shortest chain from p1 to p2. Null if there is no
     *         path from p1 to p2
     */
    public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
         if((p1.length()==0)||p2.length()==0) {
               return null;
           }
           if(g.members.length == 0) {
               return null;
           }
           if(g== null||p1==null||p2==null) {
               return null;
           }
           p1 = p1.toLowerCase();
           p1.trim();
           p2.trim();

           try {
           if(!g.members[g.map.get(p1)].name.equals(p1)||!g.members[g.map.get(p2)].name.equals(p2))
           {
              return null;
           }
           }catch(Exception e) {
               return null;
           }
           if(p1==p2) {
               ArrayList<String> temp = new ArrayList<String>();
               temp.add(p1);
               return temp;
           }
            ArrayList<String> result = new ArrayList<String>();
            //////
            
            /////
            boolean[] visited = new boolean[g.map.size()];
            String[] predecessor = new String[g.map.size()];
            Queue<Integer> queue = new Queue<Integer>();
            for(int i =0; i < visited.length;i++) {
                visited[i] = false;
                predecessor[i] = null;
            }
               int startNum = g.map.get(p1); 
            visited[startNum] = true;
            queue.enqueue(startNum);
            while(!queue.isEmpty()) {
                int v = queue.dequeue();
                for(Friend friend = g.members[v].first; friend != null; friend=friend.next) {
                    if(!visited[friend.fnum]) {
                        predecessor[friend.fnum] = g.members[v].name;
                        visited[friend.fnum] = true;
                        queue.enqueue(friend.fnum);
                    }
                }
            }
              Stack<String> resultTemp = new Stack<String>();
              
              String current = p2;
              while(current != null) {
                  
                  resultTemp.push(current);
                  current = predecessor[g.map.get(current)];
              }
              while(!resultTemp.isEmpty()) {
                  result.add(resultTemp.pop());
              }
            if (!visited[g.map.get(p2)]) {
                System.out.println("Cannot reach!");
                return null;
            }
            return result;
    }
    
    /**
     * Finds all cliques of students in a given school.
     * 
     * Returns an array list of array lists - each constituent array list contains
     * the names of all students in a clique.
     * 
     * @param g Graph for which cliques are to be found.
     * @param school Name of school
     * @return Array list of clique array lists. Null if there is no student in the
     *         given school
     */
    public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
        
        ArrayList<ArrayList<String>> resultLestofLiests  = new ArrayList<ArrayList<String>>();
        boolean[] visited = new boolean[g.members.length];
        for(int i =0; i < visited.length;i++) {
            visited[i] = false;
        }
        Queue<Integer> queue = new Queue<Integer>();
        for(int v = 0;v<visited.length;v++) {
            if(!visited[v] && g.members[v].school != null && g.members[v].school.equals(school)) {
                ArrayList<String> ResultofListOfStrings  = new ArrayList<String>();
                ResultofListOfStrings.add(g.members[v].name);
                visited[v] = true;
                queue.enqueue(v);
                while(!queue.isEmpty()) {
                    int l = queue.dequeue();
                    for(Friend frnd = g.members[l].first; frnd != null;frnd=frnd.next) {
                        int vnum = frnd.fnum;
                        if(!visited[vnum]) {
                            if(g.members[vnum].school != null) {
                            if(g.members[vnum].school.equals(school)) {
                            ResultofListOfStrings.add(g.members[vnum].name);
                            visited[vnum] = true;
                            queue.enqueue(vnum);
                            }
                            }
                        }
                    }
                    
                }
                     resultLestofLiests.add(ResultofListOfStrings);
            }
        }
   
        return resultLestofLiests;
        
    }
    
    /**
     * Finds and returns all connectors in the graph.
     * 
     * @param g Graph for which connectors needs to be found.
     * @return Names of all connectors. Null if there are no connectors.
     */
    public static ArrayList<String> connectors(Graph g) {

           Person[] members = g.members;
           Integer[] dfsnum = new Integer[members.length];
           Integer[] back = new Integer[members.length];
          ArrayList<String> result = new ArrayList<>();
           boolean[] visited = new boolean[members.length];
           boolean[] isitfirst = new boolean[members.length];
            for (int v=0; v < visited.length; v++) {
                visited[v] = false;
               isitfirst[v] = false;
            }
               isitfirst[0] = true;
            dfsnum[0] = 1;
            back[0] = 1;
            for (int v=0; v < visited.length; v++) {
                if (!visited[v]) {
                    System.out.println("\nSTARTING AT " + members[v].name + "\n");
                    if(v !=0) {
                       dfsnum[v] = 1;
                       back[v] = 1;
                          isitfirst[v] = true;
                    }
                    dfs(v, visited,members,dfsnum,back, result,isitfirst);
                }
            }
            for(int i = 0;i<dfsnum.length;i++) {
            }
            for(int i = 0;i<back.length;i++) {
               
            }
            return result;
        }
       
       private static void dfs(int v, boolean[] visited, Person[] members,Integer[] back,Integer[] dfsnum, ArrayList<String> result,boolean[] isitfirst) {
           
           visited[v] = true;
           
           System.out.println("\tvisiting " + members[v].name);
           for (Friend e=members[v].first; e != null; e=e.next) {
               if (!visited[e.fnum]) {
                   dfsnum[e.fnum] = dfsnum[v]+1;
                   back[e.fnum] = dfsnum[v]+1;
                   dfs(e.fnum, visited,members,back,dfsnum,result,isitfirst);
                if (dfsnum[v] > back[e.fnum]) {
                 back[v] = Math.min(back[v], back[e.fnum]);
             } else {
                 if(!isitfirst[v]) {
                   if (dfsnum[v] <= back[e.fnum]) { 
                     if (!result.contains(members[v].name)) {
                         result.add(members[v].name);
                     }
                 }
                 }else {
                     isitfirst[v] = false; 
                 }
             } 
               }else {
                   back[v] = Math.min(back[v], dfsnum[e.fnum]);
               }
               
               
           }
           
       }
}