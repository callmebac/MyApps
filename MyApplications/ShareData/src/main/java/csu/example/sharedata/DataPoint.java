package csu.example.sharedata;


public class DataPoint {
	private double [] dimension;
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DataPoint(double [] dimension){
		this.dimension = dimension;
	}

	public double[] getDimension() {
		return dimension;
	}

	public void setDimension(double[] dimension) {
		this.dimension = dimension;
	}



}