package WebServices;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SOAP extends AsyncTask<String,String,String> {
    static final String NAMESPACE="http://WebServices/";
    static final String METODNAME="insertarContacto";
    static final String URL="http://192.168.1.50:8084/Webservice-Soap-Restful/wscontactos?wsdl";
    static final String SOAP_ACTION=NAMESPACE+METODNAME;
    @Override
    protected String doInBackground(String... strings) {
        SoapObject request= new SoapObject(NAMESPACE,METODNAME);
        request.addProperty("nombre",strings[0]);
        request.addProperty("apellido",strings[1]);
        request.addProperty("email",strings[2]);
        request.addProperty("movil",strings[3]);
        request.addProperty("fijo",strings[4]);
        request.addProperty("trabajo",strings[5]);
        request.addProperty("ruta",strings[6]);
        request.addProperty("lat",strings[7]);
        request.addProperty("lng",strings[8]);
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte= new HttpTransportSE(URL);
        Log.d("transporte",request.toString());
        String resultado="";
        try{
            transporte.call(SOAP_ACTION,envelope);
            SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
            Log.d("Resp",response.toString());
            resultado=response.toString();
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
