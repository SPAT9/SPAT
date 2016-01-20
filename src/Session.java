import java.util.concurrent.atomic.AtomicInteger;


public class Session {
	
	int sessionID;
	AtomicInteger sessionGenerator;
	
	public Session(){
		
		sessionGenerator = new AtomicInteger();
		sessionID = sessionGenerator.getAndIncrement();	
	}
	
	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}


}
