package org.herac.tuxguitar.gui.system.language;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.herac.tuxguitar.gui.editors.chord.ChordSelector;
import org.herac.tuxguitar.gui.util.TGFileUtils;

public class TGResourceBundle {

  public static TGResourceBundle getBundle(String baseName, Locale locale) {
    Properties properties = new Properties();

    String bundleName = baseName.replace('.', '/');
    String bundleExtension = ".properties";

    // load default
    TGResourceBundle.loadResources((bundleName + bundleExtension), properties);

    // load language
    bundleName += "_";
    if (locale.getLanguage() != null && locale.getLanguage().length() > 0) {
      bundleName += locale.getLanguage();
      TGResourceBundle
          .loadResources((bundleName + bundleExtension), properties);
    }

    // load country
    bundleName += "_";
    if (locale.getCountry() != null && locale.getCountry().length() > 0) {
      bundleName += locale.getCountry();
      TGResourceBundle
          .loadResources((bundleName + bundleExtension), properties);
    }

    // load variant
    bundleName += "_";
    if (locale.getVariant() != null && locale.getVariant().length() > 0) {
      bundleName += locale.getVariant();
      TGResourceBundle
          .loadResources((bundleName + bundleExtension), properties);
    }

    return new TGResourceBundle(locale, properties);
  }

  private static void loadResources(String name, Properties p) {
    try {
      for (final URL url : TGFileUtils.getResourceUrls(name)) {
        Properties properties = new Properties();
        properties.load(url.openStream());
        p.putAll(properties);
      }
    } catch (IOException e) {
      LOG.error(e);
    }
  }
  

  /** The Logger for this class. */
  public static final transient Logger LOG = Logger
      .getLogger(TGResourceBundle.class);
  

  private Locale locale;

  private Properties properties;

  public TGResourceBundle(Locale locale, Properties properties) {
    this.locale = locale;
    this.properties = properties;
  }

  public Locale getLocale() {
    return this.locale;
  }

  public String getString(String key) {
    return this.properties.getProperty(key);
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
