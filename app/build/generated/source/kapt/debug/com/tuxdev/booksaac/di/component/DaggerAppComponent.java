// Generated by Dagger (https://google.github.io/dagger).
package com.tuxdev.booksaac.di.component;

import com.tuxdev.booksaac.di.module.AppModule;
import com.tuxdev.booksaac.di.module.NetworkModule;
import com.tuxdev.booksaac.di.module.NetworkModule_ProvideOkhttpFactory;
import com.tuxdev.booksaac.di.module.NetworkModule_ProvideRetrofitFactory;
import com.tuxdev.booksaac.di.module.NetworkModule_ProvideSamalonaServiceFactory;
import com.tuxdev.booksaac.network.BookServices;
import com.tuxdev.booksaac.ui.books.BooksActivity;
import com.tuxdev.booksaac.ui.books.BooksViewModel;
import com.tuxdev.booksaac.ui.books.BooksViewModel_MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class DaggerAppComponent implements AppComponent {
  private Provider<OkHttpClient> provideOkhttpProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<BookServices> provideSamalonaServiceProvider;

  private DaggerAppComponent(Builder builder) {
    initialize(builder);
  }

  public static AppComponent.Builder builder() {
    return new Builder();
  }

  public static AppComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideOkhttpProvider =
        DoubleCheck.provider(NetworkModule_ProvideOkhttpFactory.create(builder.networkModule));
    this.provideRetrofitProvider =
        DoubleCheck.provider(
            NetworkModule_ProvideRetrofitFactory.create(
                builder.networkModule, provideOkhttpProvider));
    this.provideSamalonaServiceProvider =
        DoubleCheck.provider(
            NetworkModule_ProvideSamalonaServiceFactory.create(
                builder.networkModule, provideRetrofitProvider));
  }

  @Override
  public void inject(BooksViewModel booksViewModel) {
    injectBooksViewModel(booksViewModel);
  }

  @Override
  public void inject(BooksActivity booksActivity) {}

  private BooksViewModel injectBooksViewModel(BooksViewModel instance) {
    BooksViewModel_MembersInjector.injectBookServices(
        instance, provideSamalonaServiceProvider.get());
    return instance;
  }

  private static final class Builder implements AppComponent.Builder {
    private NetworkModule networkModule;

    @Override
    public AppComponent build() {
      if (networkModule == null) {
        this.networkModule = new NetworkModule();
      }
      return new DaggerAppComponent(this);
    }

    /**
     * This module is declared, but an instance is not used in the component. This method is a
     * no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Override
    public Builder appModule(AppModule appModule) {
      return this;
    }

    @Override
    public Builder networkModule(NetworkModule networkModule) {
      this.networkModule = Preconditions.checkNotNull(networkModule);
      return this;
    }
  }
}