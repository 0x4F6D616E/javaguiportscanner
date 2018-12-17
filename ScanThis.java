import java.net.InetSocketAddress;
import java.net.Socket;

public class ScanThis {

	public static String start(String host,int startport,int stopport){
		String result = "";
		int subN = stopport - startport;
		int cstartport = startport;
		Portscanner1.progress = 0;
		Portscanner1.current = 0;
		for(int b = 0 ; b <= subN ; b++,cstartport++ ) {
			Portscanner1.progress +=cstartport;
		}
		
		for(int i = startport ; i <= stopport ; i ++){
			Portscanner1.current +=i;
			try{
				Socket s = new Socket();
				s.connect(new InetSocketAddress(host,i), 500);
				result += "Host: "+host+":"+i+"/tcp open \n";
				s.close();
			}catch(Exception e){
			}
			result += "Host: "+host+":"+i+"/tcp close \n";

		}
		return result;
	}

	}
