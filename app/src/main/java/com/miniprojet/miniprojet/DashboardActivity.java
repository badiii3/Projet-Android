package com.miniprojet.miniprojet;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;

import static com.miniprojet.miniprojet.R.id.navigation;

public class DashboardActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // action et its titre
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        firebaseAuth=FirebaseAuth.getInstance();
        BottomNavigationView navigationView=findViewById(navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //home fragment transaction
        actionBar.setTitle("Home");
        HomeFragment fragment =new HomeFragment();
        FragmentTransaction file = getSupportFragmentManager().beginTransaction();
        file.replace(R.id.content,fragment,"");
        file.commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NotNull MenuItem item) {
            //handle item oncliks
            switch (item.getItemId()){
                case R.id.nav_home:
                    //home fragment transaction
                    actionBar.setTitle("Home");
                    HomeFragment fragment =new HomeFragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.content,fragment,"");
                    ft1.commit();
                    return true;
                case R.id.nav_profile:
                    //profil fragment transaction

                    actionBar.setTitle("Profile");
                   ProfileFragment fragment2 =new ProfileFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.content,fragment2,"");
                    ft2.commit();
                    return true;
                case R.id.nav_users:
                    //users fragment transaction

                    actionBar.setTitle("Users");
                    UsersFragment fragment3 =new UsersFragment();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.content,fragment3,"");
                    ft3.commit();
                    return true;

                case R.id.nav_chat:
                    //users fragment transaction

                    actionBar.setTitle("Chats"); //change action bar title
                    ChatListFragment fragment4 =new ChatListFragment();
                    FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                    ft4.replace(R.id.content,fragment4,"");
                    ft4.commit();
                    return true;
            }

            return false;
        }
    };

   private void checkUserstatus(){
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (user!=null) {
        }
        else {
            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
            finish();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id== R.id.action_logout){
            firebaseAuth.signOut();

        }
        return super.onOptionsItemSelected(item);
    }
}