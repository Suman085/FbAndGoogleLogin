package com.example.user.fbandgooglelogin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 4/4/2017.
 */

public class ProfileFragment extends Fragment {
    private TextView nameField;
    private GoogleSignInAccount mSignInAccount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.profile_view,container,false);
        nameField=(TextView)view.findViewById(R.id.name);
        TextView emailField=(TextView)view.findViewById(R.id.email);
        CircleImageView mProfileView=(CircleImageView)view.findViewById(R.id.profilepic);

        if(getArguments()!=null){
            mSignInAccount=getArguments().getParcelable(MainActivity.ACCOUNT_GOOGLE);
            nameField.setText(mSignInAccount.getDisplayName());
            emailField.setText(mSignInAccount.getEmail());
            Picasso.with(getActivity()).load(mSignInAccount.getPhotoUrl()).placeholder(R.drawable.profile_icon).into(mProfileView);
        }else if(Profile.getCurrentProfile()!=null) {
            Profile profile = Profile.getCurrentProfile();
            Picasso.with(getActivity()).load(profile.getProfilePictureUri(90, 90)).fit().into(mProfileView);
            nameField.setText(profile.getName());
            emailField.setText(profile.getLinkUri().toString());
        }
        return view;
    }
}
