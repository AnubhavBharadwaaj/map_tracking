package com.example.anubhav.mapdemo;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity implements NsdManager.RegistrationListener/*, OnMapReadyCallback*/ {
    public static final String TAG = "MapDemo.MainActivity";
    private int count = 0;
    int textsize = 18;
    //String s[] = new String[20];

    NsdServiceInfo mService;
    ServerSocket mServerSocket;
    int mLocalPort;
    public String mServiceName = "NsdChat_anubhavproject";
    public String SERVICE_TYPE = "_nsdchat._tcp";
    NsdManager mNsdManager;
    NsdManager.RegistrationListener mRegistrationListener;
    NsdManager.DiscoveryListener mDiscoveryListener;
    public NsdManager.ResolveListener mResolveListener;
    //private ListView listView;
    //private ArrayAdapter<String> listAdapter;
    //ArrayList<String> peers = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        s[0] = "hello";

        setContentView(R.layout.activity_main);
        //listView = (ListView) findViewById(R.id.listView);
        // MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // mapFragment.getMapAsync(this);
        //Context.NSD_SERVICE has been replaced by this.NSD_SERVICE as it was this which was being passed
        // as context to begin with



       // locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);
       // provider = locationManager.getBestProvider(criteria, false);
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        //locationManager.requestLocationUpdates(provider, 0, 0, (android.location.LocationListener) this);
       // Location location = locationManager.getLastKnownLocation(provider);
        //onLocationChanged(location);

        mNsdManager = (NsdManager) this.getSystemService(this.NSD_SERVICE);
        initializeResolveListener();
        initializeDiscoveryListener();
        initializeRegistrationListener();


    }




    public void clickRegister(View v) throws IOException {
        initializeServerSocket();
        registerService(mLocalPort);
        TextView textView1 = new TextView(this);
        textView1.setTextSize(textsize);
        textView1.setText("Host Port::" + mLocalPort);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main);
        layout.addView(textView1);
    }

    public void clickDiscover(View v) {
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
        //peers.addAll(Arrays.asList(s));
        //listAdapter = new ArrayAdapter<String>(this, R.layout.layout_type, peers);

        //listView.setAdapter(listAdapter);
    }

    public void clickConnect(View v) {
        NsdServiceInfo service = mService;

        if (service != null) {
            Log.d(TAG, "Connecting.");

            connectToServer(service.getHost(), service.getPort());

        } else {
            Log.d(TAG, "No service to connect to!");
        }
        TextView textView2 = new TextView(this);
        textView2.setTextSize(textsize);
        textView2.setText("Peer Port::" + mService.getPort() + mService.getServiceName());
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main);
        layout.addView(textView2);

    }

    public void onClickMap(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    public void onClickGps(View v)
    {
        Intent i=new Intent(this,GpsData.class);
        startActivity(i);
    }

    public void connectToServer(InetAddress address, int port) {
        //mChatClient = new ChatClient(address, port);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(mServiceName);
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(port);

        //mNsdManager = Context.getSystemService(Context.NSD_SERVICE);
        mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);

    }

    //gets any available port for the app, passing 0 as the argument helps generate default port number whichever available
    public void initializeServerSocket() throws IOException {

        mServerSocket = new ServerSocket(0);
        mLocalPort = mServerSocket.getLocalPort();
    }

    public void initializeRegistrationListener() {
        mRegistrationListener = new NsdManager.RegistrationListener()

        {
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

                mServiceName = serviceInfo.getServiceName();
                //  mServiceName = NsdServiceInfo.getServiceName();
            }

            public void onUnregistrationFailed(NsdServiceInfo serviceInfo) {

            }


            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

            }

            public void onServiceRegistered(NsdServiceInfo serviceInfo) {

            }

            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {

            }
        };
    }

    public void initializeDiscoveryListener() {


        mDiscoveryListener = new NsdManager.DiscoveryListener() {


            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");

            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found!  Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                //final NsdServiceInfo service1=service;
                //runOnUiThread(new Runnable() {
                //  @Override
                //public void run() {
                //  listAdapter.add( service1.getServiceName() );
                //}
                //});


               // count++;
                //s[count] = service.getServiceName();
                //count++;
                /*Context context=getApplicationContext();
                CharSequence text=(service.getServiceName());
                int duration= Toast.LENGTH_SHORT;
                Toast toast=Toast.makeText(context,text,duration);
                toast.show();*/


                if (!service.getServiceType().equals(service.getServiceType())) {

                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(mServiceName)) {

                    Log.d(TAG, "Same machine: " + mServiceName);
                } else if (service.getServiceName().contains("NsdChat")) {

                    mNsdManager.resolveService(service, mResolveListener);

                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {

                Log.e(TAG, "service lost" + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {

                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(mServiceName)) {

                    Log.d(TAG, "Same IP.");
                    return;
                }

                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();

            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNsdManager.unregisterService(mRegistrationListener);
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates((android.location.LocationListener) this);*/
    }

    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

    }

    @Override
    public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

    }

    @Override
    public void onServiceRegistered(NsdServiceInfo serviceInfo) {

    }

    @Override
    public void onServiceUnregistered(NsdServiceInfo serviceInfo) {

    }







  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("marker"));
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(this, ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    public static void requestPermission(AppCompatActivity activity, String requestId,
                                         String permission, boolean finishActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {


        } else {
            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(activity, new String[]{permission}, Integer.parseInt(requestId));


        }
    }

*/

}