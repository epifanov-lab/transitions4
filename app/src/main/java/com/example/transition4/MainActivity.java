package com.example.transition4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ru.realtimetech.webka.client.Client;

import android.content.Context;
import android.os.Bundle;

import com.webka.sdk.data.DataSources;
import com.webka.sdk.data.LocalStorage;
import com.webka.sdk.players.WebkaPlayerFactory;
import com.webka.sdk.players.WebkaPlayers;
import com.webka.sdk.webrtc.WebRTC;

import org.webrtc.ContextUtils;

import javax.inject.Singleton;

@Module
public class MainActivity extends AppCompatActivity {

  private WebkaPlayerFactory mWebkaPlayerFactory;

  /** MainActivity Injector. */
  public final Injector mInjector =
    DaggerMainActivity_Injector
      .builder()
      .mainActivity(this)
      .build();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ContextUtils.initialize(context());
    mWebkaPlayerFactory = mInjector.players();

    getSupportFragmentManager().beginTransaction()
                               .replace(R.id.replace_fragment, Fragment1.instance())
                               .addToBackStack(null)
                               .commit();
  }

  /** @return as application context */
  @Provides
  @NonNull
  Context context() {
    return getApplicationContext();
  }

  @Override
  public Object getSystemService(@NonNull String name) {
    if (WebkaPlayerFactory.NAME.equals(name)) return mInjector.players();
    if (WebRTC.NAME.equals(name)) return mWebkaPlayerFactory;
    else return super.getSystemService(name);
  }

  /** Application activity. */
  @Component(modules = {MainActivity.class, DataSources.class, WebkaPlayers.class})
  @Singleton
  public static abstract class Injector {
    public abstract Context context();
    public abstract Client client();
    public abstract LocalStorage storage();
    public abstract WebkaPlayerFactory players();
    public abstract WebRTC.Factory webrtc();
  }

  /** @return DataSources Configuration */
  @Provides
  @Singleton
  @NonNull
  DataSources.Config config() {
    return new DataSources.Config(
      BuildConfig.DEBUG,
      BuildConfig.VERSION_CODE,
      "{\n" +
        "  \"api\": {\n" +
        "    \"connect\": \"https://api.webka.com\",\n" +
        "     \"apipath\": \"/api/v1/\"\n" +
        "  },\n" +
        "  \"ws_router\": {\n" +
        "    \"connect\": \"https://webka.com/\",\n" +
        "    \"path\": \"/wss\"\n" +
        "  },\n" +
        "  \"storage\": {\n" +
        "    \"domain\": \"https://storage.webka.com\"\n" +
        "  },\n" +
        "  \"www\": {\n" +
        "    \"domain\": \"https://webka.com/\"\n" +
        "  }\n" +
        "}",
      null
    );
  }

}
