package elem;

public class Camera {

	private int x;
	private int y;
	private int z;
	private int zoomX;
	private int zoomY;

	public Camera(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		zoomX = 0;
		zoomY = 0;
	}

	public void translateX(int x) {
		this.x += x;
	}

	public void translateY(int y) {
		this.y += y;
	}

	public void translateZ(int z) {
		int check = this.z + z;
		if (check <= 0)
			this.z = 0;
		else if (check >= 250)
			this.z = 250;
		else
			this.z += z;
	}

	public int getX() {
		return x - zoomX;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y - zoomY;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void setZoomX(int zoomX) {
		this.zoomX = zoomX;
	}

	public void setZoomY(int zoomY) {
		this.zoomY = zoomY;
	}
}
