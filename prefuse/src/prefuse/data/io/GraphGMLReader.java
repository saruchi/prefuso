package prefuse.data.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

import prefuse.data.Graph;
import prefuse.data.Node;

public class GraphGMLReader extends AbstractGraphReader  implements GraphReader {

	@Override
	public Graph readGraph(InputStream is) throws DataIOException {
		// TODO Auto-generated method stub
		return null;
	}
	public Graph readGraph(String file) throws DataIOException
	{
		Graph graph=null;
		BufferedReader in;
		int f=0;
		if(file.equals("polblogs.gml")){
			f=1;
		}
		
		try {
			in = new BufferedReader(new FileReader(file));
			String t, column="";
			
			t=in.readLine();
			boolean flag = true;
			int index, source, target;
			
			//boolean directed =false;
			while (t!=null)
			{
				if (t.matches("..directed.."))
				{
					if (t.charAt(11)=='0')
						graph = new Graph(false);
					else
						graph = new Graph(true);
				}
				else if (t.matches("  node"))
				{
					Node n = graph.addNode();
					if (flag)
					{
						in.readLine();
						t = in.readLine();
						while (!t.equals("  ]"))
							
						{
							//System.out.println(t);
							t = t.substring(4,t.length());
							index = t.indexOf(" ");
							column = t.substring(0,index);
							
							if (t.charAt(index+1)== '"')
							{
								graph.addColumn(column, String.class);
								n.set(column, t.substring(index+2,t.length()-1));
							}
							else
							{
								graph.addColumn(column, Integer.class);
								n.set(column, Integer.parseInt(t.substring(index+1,t.length()))-f);
							}
							t = in.readLine();
						}
						flag = false;
					}
					else
					{
						in.readLine();
						t = in.readLine();
						while (!t.equals("  ]"))
						{
							t = t.substring(4,t.length());
							index = t.indexOf(" ");
							column = t.substring(0,index);
							if (t.charAt(index+1)== '"')
							{
								n.set(column, t.substring(index+2,t.length()-1));
							}
							else
							{
								//System.out.println(t.substring(index+1,t.length()));
								n.set(column, Integer.parseInt(t.substring(index+1,t.length()))-f);
							}
							t = in.readLine();
						}
					}
				}
				else if (t.matches("  edge"))
				{
					in.readLine();
					t = in.readLine();
					source = Integer.parseInt(t.substring(11,t.length()));
					t = in.readLine();
					target = Integer.parseInt(t.substring(11,t.length()));
					//System.out.println(source + "+"+target);
					graph.addEdge(source-f,target-f);
					in.readLine();
				}	
				t=in.readLine();
			}
			return graph;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

}
