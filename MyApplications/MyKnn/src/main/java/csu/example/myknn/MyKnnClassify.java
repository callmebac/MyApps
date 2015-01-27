package csu.example.myknn;



import java.util.ArrayList;

import android.util.Log;

public class MyKnnClassify {

	private MyKnnCluster knnclusters [];
	private ArrayList<MyKnnNode> KnnPoints = new ArrayList<MyKnnNode>();
	
	public MyKnnClassify(MyKnnCluster knnclusters [],ArrayList<MyKnnNode> KnnPoints ){
		this.knnclusters = knnclusters;
		this.KnnPoints = KnnPoints;
	}
	
	

	//��������㵽���е���������
		public void calEuclideanDistanceSum(){
//System.out.println(knnclusters[0].getClusterName());
//System.out.println(knnclusters[1].getClusterName());
//System.out.println(knnclusters[2].getClusterName());
			double temp = 0.0;
			double sum = 0.0;
			for (int i = 0; i < KnnPoints.size(); i++) {
					double [] dims = KnnPoints.get(i).getDimension();
					KnnPoints.get(i).setInstance(Double.MAX_VALUE);
					KnnPoints.get(i).setClassification("null");
//Log.i( "��������"+i,"-------------------------------���Ե㣺" + i);	
//System.out.println( "-------------------------------���Ե㣺" + i );				
//System.out.println( " ��ʼ����" + KnnPoints.get(i).getInstance());
//System.out.println(" ��ʼ����  " + KnnPoints.get(i).getClassification());
//		Log.i( " ��ʼ����" ," " + KnnPoints.get(i).getInstance());
//		Log.i (" ��ʼ����  " ," " + KnnPoints.get(i).getClassification());			
//System.out.println("dims"+dims[0]);
//System.out.println("dims"+dims[1]);
//System.out.println("dims"+dims[2]);
//		Log.i("dims"," " + dims[0]);
//		Log.i("dims"," " + dims[1]);
//		Log.i("dims"," " + dims[2]);
					for (int n = 0; n < knnclusters.length; n++) {			
						MyKnnCluster cluster = knnclusters[n];
						ArrayList<MyKnnNode> KnnPointsFromcluster = cluster.getKnnPoints();
//System.out.println(" knnclustersName"+ knnclusters[n].getClusterName());
//		Log.i("knnclustersName", " " + knnclusters[n].getClusterName());
					for (int k = 0; k < KnnPointsFromcluster.size(); k++) {
							for (int j = 0; j < dims.length; j++) {
//System.out.println("KnnPointsFromcluster"+KnnPointsFromcluster.get(k).getDimension()[j]);
//		Log.i("KnnPointsFromcluster", " " + KnnPointsFromcluster.get(k).getDimension()[j]);
							temp  =  Math.pow(dims[j]-KnnPointsFromcluster.get(k).getDimension()[j], 2);	
//System.out.println("temp " + temp );
//		Log.i("temp", " " + temp);
							sum  = sum + temp;
//System.out.println("sum " + sum);
//		Log.i("sum", " " + sum);
							}		
							double EuclideanDistanceSum = Math.sqrt(sum);
//System.out.println("EuclideanDistanceSum  EuclideanDistanceSum);
//System.out.println( " �м���̾����ǣ�" + KnnPoints.get(i).getInstance());
//		Log.i("EuclideanDistanceSum"," " + EuclideanDistanceSum);
//		Log.i(" �м���̾����ǣ�"," " + KnnPoints.get(i).getInstance());
							if( EuclideanDistanceSum < KnnPoints.get(i).getInstance() ){
								KnnPoints.get(i).setInstance(EuclideanDistanceSum);
//System.out.println( " �����ǣ�" + KnnPoints.get(i).getInstance());
//		Log.i(" �����ǣ�"," " +  KnnPoints.get(i).getInstance());
								KnnPoints.get(i).setClassification(cluster.getClusterName());
//System.out.println("����ǣ�  " + KnnPoints.get(i).getClassification());	
//		Log.i(" ����ǣ�"," " + KnnPoints.get(i).getClassification());
								sum = 0.0;
								temp = 0.0;
							}else{
								sum = 0.0;
								temp = 0.0;
							}						
					}
				}
			}
			}
	
			
	

}
