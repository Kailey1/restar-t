package cooldudes.restart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ResourcesFragment extends Fragment {


    public ResourcesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resources, null);

        ImageButton card1 = v.findViewById(R.id.alcohol_use_card);
        ImageButton card2 = v.findViewById(R.id.services_card);
        ImageButton card3 = v.findViewById(R.id.reduce_card);
        ImageButton mapCard = v.findViewById(R.id.map_card);
        ImageButton groupCard = v.findViewById(R.id.group_card);

        groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        mapCard.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         // opens google maps for navigation
                                         Uri gmmIntentUri = Uri.parse("geo:0,0?q=alcoholics+anonymous");
                                         Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                         mapIntent.setPackage("com.google.android.apps.maps");
                                         startActivity(mapIntent);
                                     }
                                 });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AlcoholUseActivity.class);
                startActivity(i);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ServiceActivity.class);
                startActivity(i);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReduceAlcoholConsumptionActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(),"activity started", Toast.LENGTH_SHORT);
            }
        });

        return v;
    }
}