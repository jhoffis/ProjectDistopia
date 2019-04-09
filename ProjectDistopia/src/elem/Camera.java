package elem;

public class Camera {

	private double x;
	private double y;
	private double z;
	private double zoomX;
	private double zoomY;

	public Camera(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		zoomX = 0;
		zoomY = 0;
	}

	public void translateX(double x) {
		this.x += x;
	}

	public void translateY(double y) {
		this.y += y;
	}

	public void translateZ(double z) {
		double check = this.z + z;
		if (check <= 0)
			this.z = 0;
		else if (check >= 250)
			this.z = 250;
		else
			this.z += z;
	}

	public double getX() {
		return x - zoomX;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y - zoomY;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setZoomX(double zoomX) {
		this.zoomX = zoomX;
	}

	public void setZoomY(double zoomY) {
		this.zoomY = zoomY;
	}
}
