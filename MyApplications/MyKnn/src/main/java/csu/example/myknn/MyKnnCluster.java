package csu.example.myknn;




import java.util.ArrayList;



public class MyKnnCluster {

	private String clusterName;//����

	private ArrayList<MyKnnNode> KnnPoints = new ArrayList<MyKnnNode>();
	
	public MyKnnCluster(String clusterName,ArrayList<MyKnnNode> KnnPoints){
		this.clusterName = clusterName;
		this.KnnPoints = KnnPoints;
	}
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public ArrayList<MyKnnNode> getKnnPoints() {
		return KnnPoints;
	}
	public void setKnnPoints(ArrayList<MyKnnNode> knnPoints) {
		KnnPoints = knnPoints;
	}
}
