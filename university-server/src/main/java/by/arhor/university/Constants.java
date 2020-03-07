package by.arhor.university;

public final class Constants {

  private Constants() { throw new UnsupportedOperationException("Must not be instantiated"); }

  public static final long SERIAL_VERSION = 1L;

  public static final String REST_API_V_1 = "/api/v1";

  public static final String CACHE_USERS = "cache_users";
  public static final String CACHE_LANGS = "cache_langs";
  public static final String CACHE_ROLES = "cache_roles";

  public static final byte[]    EMPTY_ARRAY_BYTE   = new byte[0];
  public static final short[]   EMPTY_ARRAY_SHORT  = new short[0];
  public static final int[]     EMPTY_ARRAY_INT    = new int[0];
  public static final long[]    EMPTY_ARRAY_LONG   = new long[0];
  public static final float[]   EMPTY_ARRAY_FLOAT  = new float[0];
  public static final double[]  EMPTY_ARRAY_DOUBLE = new double[0];
  public static final char[]    EMPTY_ARRAY_CHAR   = new char[0];
  public static final boolean[] EMPTY_ARRAY_BOOL   = new boolean[0];

  public static final Object[] EMPTY_ARRAY = new Object[0];

}
