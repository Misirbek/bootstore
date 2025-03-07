package com.example.bootstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar tbAppBar;
    TextView tvCompanyDetails;
    Button btnGoToProducts;
    protected SharedPreferences mSharedPreferences;
    private  long mBackPressedTime;
    private  Intent mIntent;
    private  int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = Utility.getPreference(this);

        //tvCompanyDetails = (TextView)findViewById(R.id.tv_company_details);
        btnGoToProducts = (Button) findViewById(R.id.btn_product_explore);
        tbAppBar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar_tool);

        setSupportActionBar(tbAppBar);
        setupNavigation(savedInstanceState, tbAppBar);

        btnGoToProducts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent gotoProducts = new Intent(MainActivity.this,ProductsActivity.class);
                startActivity(gotoProducts);
            }
        });


    }
    private void setupNavigation(Bundle savedInstanceState, android.support.v7.widget.Toolbar toolbar)
    {
        //Typeface face = Typeface.createFromAsset(getAssets(), "fonts/lato_regular.ttf");

        // Navigation menu items
        List<IDrawerItem> iDrawerItems = new ArrayList<>();
        iDrawerItems.add(new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_black_24dp));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Products").withIcon(R.drawable.ic_our_products));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Cart").withIcon(R.drawable.ic_cart));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Contact Us").withIcon(R.drawable.ic_contact_us));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Share").withIcon(R.drawable.ic_share_black_24dp));


        //iDrawerItems.add(new PrimaryDrawerItem().withName("Notes").withIcon(R.drawable.ic_note_black_24dp));

        // sticky DrawItems ; footer menu items

        List<IDrawerItem> bottomItems = new ArrayList<>();

        bottomItems.add(new PrimaryDrawerItem().withName(getString(R.string.app_version)));
        // navigation menu header
        AccountHeader header = new AccountHeaderBuilder().withActivity(MainActivity.this)
                .addProfiles(new ProfileDrawerItem()
                        .withEmail(mSharedPreferences.getString(Utility.USEREMAIL, ""))
                        .withName(mSharedPreferences.getString(Utility.USERNAME, ""))
                        .withIcon(R.drawable.ic_avtar_48dp))
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.drawable.card_bg)
                .withSelectionListEnabledForSingleProfile(true) // we need just one profile
                .build();

        // Navigation drawer
        new DrawerBuilder()
                .withActivity(MainActivity.this) // activity main
                .withToolbar(toolbar) // toolbar
                .withSavedInstance(savedInstanceState) // saveInstance of activity
                .withDrawerItems(iDrawerItems) // menu items
                .withTranslucentNavigationBar(true)
                .withStickyDrawerItems(bottomItems) // footer items
                .withAccountHeader(header) // header of navigation
                //.withOnDrawerItemClickListener(this) // listener for menu items click
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
                {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {
                        if(position == 4)
                        {
                            Uri contactUri = Uri.parse("https://www.instagram.com/bhumilll/");
                            Intent contactIntent = new Intent(Intent.ACTION_VIEW, contactUri);
                            startActivity(contactIntent);
                        }
                        else if(position == 2)
                        {
                            Intent productsIntent = new Intent(MainActivity.this, ProductsActivity.class);
                            startActivity(productsIntent);
                        }
                        else if(position == 3)
                        {
                            Intent productsIntent = new Intent(MainActivity.this, OrderListActivity.class);
                            startActivity(productsIntent);
                        }
                        else if(position == 5)
                        {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.st_share_message));
                            startActivity(Intent.createChooser( i, "Select App"));
                        }
                        return true;
                    }
                }) // listener for menu items click
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu1)
    {
        getMenuInflater().inflate(R.menu.action_logout, menu1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        mSharedPreferences.edit().clear().commit();
        Utility.ClearAll();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
//
//    public boolean onMenuItemClickListener(View view, int position, IDrawerItem drawerItem)
//    {
//        if(position == 2)
//        {
//            Uri contactUri = Uri.parse("https://devpurkarenergy.com/contact-us/");
//            Intent contactIntent = new Intent(Intent.ACTION_VIEW, contactUri);
//            startActivity(contactIntent);
//        }
//        else if(position == 3)
//        {
//            //Uri productsUri = Uri.parse("https://devpurkarenergy.com/products-2/");
//            //Intent productsIntent = new Intent(Intent.ACTION_VIEW, productsUri);
//            Intent productsIntent = new Intent(MainActivity.this, ProductsActivity.class);
//            startActivity(productsIntent);
//        }
//        else if(position == 4)
//        {
//            Intent i = new Intent(android.content.Intent.ACTION_SEND);
//            i.setType("text/plain");
//            i.putExtra(android.content.Intent.EXTRA_TEXT, "Save you energy with Devpurkar Energy. I highly recommend you to use their products and mobile applications for Home Automation. Check out www.devpurkarenergy.com");
//            startActivity(Intent.createChooser( i, "Select App"));
//        }
//        return false;
//    }

    public void onBackPressed()
    {
        mBackPressedTime = System.currentTimeMillis();
        if(counter == 0)
        {
            Toast.makeText(MainActivity.this, getString(R.string.st_back_pressed), Toast.LENGTH_SHORT).show();
            counter = 1;
        }
        else
        {
            if (mBackPressedTime + 5000 > System.currentTimeMillis())
            {
                mIntent = new Intent(Intent.ACTION_MAIN);
                mIntent.addCategory(Intent.CATEGORY_HOME);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
                super.onBackPressed();

            } else
            {
                Toast.makeText(MainActivity.this, getString(R.string.st_back_pressed), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
