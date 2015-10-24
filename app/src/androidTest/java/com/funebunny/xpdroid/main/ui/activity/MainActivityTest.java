package com.funebunny.xpdroid.main.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;

import com.funebunny.xpdroid.R;
import com.funebunny.xpdroid.business.gasto.model.GastoFavorito;
import com.funebunny.xpdroid.business.gasto.service.ServicioGastosBusiness;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by provirabosch on 17/10/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;
    private ServicioGastosBusiness servicioGastosBusiness;

    public MainActivityTest() {
        super(MainActivity.class);
        servicioGastosBusiness = new ServicioGastosBusiness();
        borrarTablaGastoFavorito();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mainActivity = getActivity();
    }

    public void testInicio() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(0)
                .perform(click());

        onView(allOf(withText("Inicio"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onView(withId(R.id.action_crear_gasto)).perform(click());
    }

    public void testHistorial() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(1)
                .perform(click());

        onView(allOf(withText("Historial"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onView(withId(R.id.action_crear_gasto)).perform(click());
    }

    public void testFavoritos() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(2)
                .perform(click());

        onView(allOf(withText("Favoritos"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onView(withId(R.id.action_crear_gasto_favorito)).perform(click());

    }

    public void testProgramables() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(3)
                .perform(click());

        onView(allOf(withText("Programables"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onView(withId(R.id.action_crear_gasto_programable)).perform(click());
    }

    public void testPresupuesto() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(4)
                .perform(click());

        onView(allOf(withText("Presupuesto"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onView(withId(R.id.action_crear_presupuesto)).perform(click());
    }

    public void testTotales() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(5)
                .perform(click());

        onView(allOf(withText("Totales"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_totalesitem_list_rb_diario)).perform(click());
        onView(withId(R.id.fragment_totalesitem_list_rb_semanal)).perform(click());
        onView(withId(R.id.fragment_totalesitem_list_rb_mensual)).perform(click());
        onView(withId(R.id.fragment_totalesitem_list_rb_anual)).perform(click());
    }

    public void testAcercaDe() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(6)
                .perform(click());

        onView(allOf(withText("Acerca de"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
    }

    public void testCrearGastoPorFavorito() {

        servicioGastosBusiness.guardarGastoFavorito("Favorito","10","Varios");
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openDrawer();
            }
        });

        onData(anything())
                .inAdapterView(withId(R.id.navigation_drawer))
                .atPosition(0)
                .perform(click());

        onView(allOf(withText("Inicio"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(withId(R.id.fragment_main_gv_favoritos))
                .atPosition(0)
                .perform(click());

    }

    private void openDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) mainActivity.findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void borrarTablaGastoFavorito() {
        List<GastoFavorito> gastoFavoritoList = servicioGastosBusiness.obtenerGastosFavoritos();

        for (GastoFavorito gastoFavorito : gastoFavoritoList) {
            servicioGastosBusiness.eliminarGastoFavorito(gastoFavorito.getId());
        }
    }

}
