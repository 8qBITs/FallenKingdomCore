package me.qbit.core.ChunkLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;

import me.qbit.core.Main;

public class MysqlMethods {

	private static String DATABASE_URL;
	  
	  private static String DATABASE_USERNAME;
	  
	  private static String DATABASE_PASSWORD;
	  
	  private static boolean useMySQL;
	  
	  private static Connection getConnection() throws SQLException {
	    if (useMySQL)
	      return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD); 
	    try {
	      Class.forName("org.sqlite.JDBC");
	      return DriverManager.getConnection("jdbc:sqlite:plugins/ChunkLoader/ChunkLoader.db");
	    } catch (ClassNotFoundException ex) {
	      Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	      return null;
	    } 
	  }
	  
	  public static void setupMysql(ChunkHolder plugin, boolean useMySQL) {
	    FileConfiguration config = Main.getMain().getConfig();
	    MysqlMethods.useMySQL = useMySQL;
	    DATABASE_URL = "jdbc:mysql://" + config.getString("DatabaseAddress") + ":" + config.getString("DatabasePort") + "/" + config.getString("DatabaseName");
	    DATABASE_USERNAME = config.getString("DatabaseUserName");
	    DATABASE_PASSWORD = config.getString("DatabasePassword");
	    setupTable();
	  }
	  
	  public static void addChunk(Chunk chunk, String owner, boolean isPersonalAnchor) {
	    Connection con = null;
	    PreparedStatement prest = null;
	    String Location = chunk.getWorld().getName() + ";" + chunk.getX() + ";" + chunk.getZ();
	    try {
	      con = getConnection();
	      prest = con.prepareStatement("INSERT INTO `ChunkDatabase`(`Location`, `Owner`, `isPersonalAnchor`) VALUES (?,?,?);");
	      prest.setString(1, Location);
	      prest.setString(2, owner);
	      if (isPersonalAnchor) {
	        prest.setInt(3, 1);
	      } else {
	        prest.setInt(3, 0);
	      } 
	      prest.executeUpdate();
	    } catch (SQLException ex) {
	      Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	    } finally {
	      try {
	        if (prest != null)
	          prest.close(); 
	        if (con != null)
	          con.close(); 
	      } catch (SQLException ex) {
	        Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	      } 
	    } 
	  }
	  
	  public static boolean RemoveChunk(Chunk chunk) {
	    boolean success = false;
	    String location = chunk.getWorld().getName() + ";" + chunk.getX() + ";" + chunk.getZ();
	    Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    try {
	      con = getConnection();
	      st = con.createStatement();
	      rs = st.executeQuery("SELECT * FROM `ChunkDatabase`");
	      while (rs.next()) {
	        if (rs.getString("Location").equals(location)) {
	          String sql = "DELETE FROM `ChunkDatabase` WHERE ID =" + rs.getInt("ID");
	          st.executeUpdate(sql);
	          success = true;
	          break;
	        } 
	      } 
	    } catch (SQLException ex) {
	      Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	    } finally {
	      try {
	        if (rs != null)
	          rs.close(); 
	        if (st != null)
	          st.close(); 
	        if (con != null)
	          con.close(); 
	      } catch (SQLException ex) {
	        Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	      } 
	    } 
	    return success;
	  }
	  
	  public static ArrayList<ChunkHolder> GetAllChunks() {
	    Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    ArrayList<ChunkHolder> myChunkHolders = new ArrayList<ChunkHolder>();
	    try {
	      con = getConnection();
	      st = con.createStatement();
	      rs = st.executeQuery("SELECT * FROM `ChunkDatabase`");
	      while (rs.next())
	        myChunkHolders.add(new ChunkHolder(rs.getString("Location"), rs.getString("Owner"), rs.getBoolean("isPersonalAnchor"))); 
	    } catch (SQLException ex) {
	      Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	    } finally {
	      try {
	        if (rs != null)
	          rs.close(); 
	        if (st != null)
	          st.close(); 
	        if (con != null)
	          con.close(); 
	      } catch (SQLException ex) {
	        Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	      } 
	    } 
	    return myChunkHolders;
	  }
	  
	  private static void setupTable() {
	    try {
	      Connection con = getConnection();
	      Statement st = con.createStatement();
	      if (useMySQL) {
	        st.executeUpdate("CREATE TABLE IF NOT EXISTS ChunkDatabase(ID INT PRIMARY KEY AUTO_INCREMENT, Location TEXT, Owner TEXT, isPersonalAnchor INT, CreationDate TIMESTAMP) ENGINE=InnoDB;");
	      } else {
	        st.executeUpdate("CREATE TABLE IF NOT EXISTS ChunkDatabase(ID INT PRIMARY KEY, Location TEXT, Owner TEXT, isPersonalAnchor INT, CreationDate TIMESTAMP);");
	      } 
	      con.close();
	    } catch (SQLException ex) {
	      Logger.getLogger(MysqlMethods.class.getName()).log(Level.SEVERE, (String)null, ex);
	    } 
	  }
	
}
