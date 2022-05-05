
package com.admin.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import jakarta.servlet.http.Part;
import java.sql.PreparedStatement;
import com.admin.bean.Places;
import java.sql.DriverManager;
import java.sql.Connection;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(fileSizeThreshold = 10485760, maxFileSize = 52428800L, maxRequestSize = 104857600L)
public class PlacesDao
{
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_ahmedabad", "root", "9288");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
    
    public static int save(final Places u) {
        int status = 0;
        try {
            final Connection con = getConnection();
            final PreparedStatement ps = con.prepareStatement("insert into destination(place_name,time,description,location,image) values(?,?,?,?,?)");
            ps.setString(1, u.getPname());
            ps.setString(2, u.getTime());
            ps.setString(3, u.getDesc());
            ps.setString(4, u.getLoc());
            final Part filepart = u.getImage();
            final InputStream fileContent = filepart.getInputStream();
            ps.setBlob(5, fileContent);
            status = ps.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
    public static int update(final Places u) {
        int status = 0;
        try {
            final Connection con = getConnection();
            final PreparedStatement ps = con.prepareStatement("update destination set place_name=?,time=?,description=?,location=? where place_name=?");
            ps.setString(1, (u.getPname()=="null")?"":u.getPname());
            ps.setString(2, (u.getTime()=="null")?"":u.getTime());
            ps.setString(3, (u.getDesc()==null)?"":u.getDesc());
            ps.setString(4, (u.getLoc()=="null")?"":u.getLoc());
            ps.setString(5, (u.getPname()=="null")?"":u.getPname());
            status = ps.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
    public static int delete(final Places u) {
        int status = 0;
        try {
            final Connection con = getConnection();
            final PreparedStatement ps = con.prepareStatement("delete from destination where place_name=?");
            ps.setString(1, u.getPname());
            status = ps.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
    public static List<Places> getAllRecords() {
        final List<Places> list = new ArrayList<Places>();
        try {
            final Connection con = getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from destination");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final Places u = new Places();
                u.setPname(rs.getString("place_name"));
                u.setTime(rs.getString("time"));
                u.setDesc(rs.getString("description"));
                u.setLoc(rs.getString("location"));
                final Blob blob = rs.getBlob("image");
                System.out.print(blob);
                final InputStream InputStream = blob.getBinaryStream();
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                final byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = InputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                final byte[] imageBytes = outputStream.toByteArray();
                final String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                u.setBase64Image(base64Image);
                list.add(u);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public static Places getRecordById(final String pname) {
        Places u = null;
        try {
            final Connection con = getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from destination where place_name=?");
            ps.setString(1, pname);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new Places();
                u.setPname(rs.getString("place_name"));
                u.setTime(rs.getString("time"));
                u.setDesc(rs.getString("description"));
                u.setLoc(rs.getString("location"));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return u;
    }
}
