package org.herac.tuxguitar.util;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;

public class TGLibraryLoader {

  private static TGLibraryLoader instance;

  /** The Logger for this class. */
  public static final transient Logger LOG = Logger
      .getLogger(TGLibraryLoader.class);

  public static TGLibraryLoader instance() {
    if (instance == null) {
      instance = new TGLibraryLoader();
    }
    return instance;
  }

  private TGLibraryLoader() {
    super();
  }

  public void loadLibraries(File folder, final String prefix,
      final String extension) {
    if (folder != null && folder.exists()) {
      FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          if (name == null) {
            return false;
          }
          if (prefix != null && prefix.length() > 0) {
            int index = name.indexOf(prefix);
            if (index != 0) {
              return false;
            }
          }
          if (extension != null && extension.length() > 0) {
            int index = name.indexOf(extension);
            if (index != (name.length() - extension.length())) {
              return false;
            }
          }
          return true;
        }
      };
      String[] files = folder.list(filter);
      for (int i = 0; i < files.length; i++) {
        File file = new File(folder.getAbsolutePath() + File.separator
            + files[i]);
        if (file.exists() && !file.isDirectory()) {
          loadLibrary(file);
        }
      }
    }
  }

  protected void loadLibrary(File file) {
    try {
      LOG.debug("Loading: " + file.getAbsolutePath());
      System.load(file.getAbsolutePath());
    } catch (Throwable throwable) {
      LOG.error(throwable);
    }
  }

}
