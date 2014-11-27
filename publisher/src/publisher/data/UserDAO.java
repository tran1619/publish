package publisher.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAO extends DataAccessObject {
   private static UserDAO instance = new UserDAO();

   public static UserDAO getInstance() {
      return instance;
   }

   private User read(ResultSet rs) throws SQLException
   {
      Long id = new Long(rs.getLong("id"));
      String username = rs.getString("username");
      String password = rs.getString("password");
      String accessKey = rs.getString("accesskey");
      User user = new User();
      user.setId(id);
      user.setUsername(username);
      user.setPassword(password);
      user.setAccessKey(accessKey);
      return user;
   }
 
   public User find(Long id)
   {
      ResultSet rs = null;
      PreparedStatement statement = null;
      Connection connection = null;
      try
      {
         connection = getConnection();
         String sql = "select * from user where id=?";
         statement = connection.prepareStatement(sql);
         statement.setLong(1, id.longValue());
         rs = statement.executeQuery();
         if (!rs.next())
         {
            return null;
         }
         return read(rs);
      }
      catch (SQLException e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         close(rs, statement, connection);
      }
   }
   
   public User findByUsername(String username)
   {
      ResultSet rs = null;
      PreparedStatement statement = null;
      Connection connection = null;
      try
      {
         connection = getConnection();
         String sql = "select * from user where username=?";
         statement = connection.prepareStatement(sql);
         statement.setString(1, username);
         rs = statement.executeQuery();
         if (!rs.next())
         {
            return null;
         }
         return read(rs);
      }
      catch (SQLException e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         close(rs, statement, connection);
      }
   }
   
   public List<User> findAll() 
   {
      LinkedList<User> users = new LinkedList<User>();
      ResultSet rs = null;
      PreparedStatement statement = null;
      Connection connection = null;
      try
      {
         connection = getConnection();
         String sql = "select * from user order by id";
         statement = connection.prepareStatement(sql);
         rs = statement.executeQuery();
         while (rs.next())
         {
            User user = read(rs);
            users.add(user);
         }
         return users;
      }
      catch (SQLException e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         close(rs, statement, connection);
      }
   }

   public void update(User user)
   {
      PreparedStatement statement = null;
      Connection connection = null;
      try
      {
         connection = getConnection();
         String sql = "update user set " + "password=? where id=?";
         statement = connection.prepareStatement(sql);
         statement.setString(1, user.getPassword());
         statement.setLong(2, user.getId().longValue());
         statement.executeUpdate();
      } catch (SQLException e)
      {
         throw new RuntimeException(e);
      } finally
      {
         close(statement, connection);
      }
   }
   
   public void create(User user)
   {
      Long id = getUniqueId();
      user.setId(id);
      PreparedStatement statement = null;
      Connection connection = null;
      try
      {
         connection = getConnection();
         String sql = "insert into user " + "(id, username, password) "

               + "values (?, ?, ?)";
         statement = connection.prepareStatement(sql);
         statement.setLong(1, id.longValue());
         statement.setString(2, user.getUsername());
         statement.setString(3, user.getPassword());
         statement.executeUpdate();
      } catch (SQLException e)
      {
         throw new RuntimeException(e);
      } finally
      {
         close(statement, connection);
      }
   }
   
   public void delete(User user)
   {
      PreparedStatement statement = null;
      Connection connection = null;
      try
      {
         connection = getConnection();
         String sql = "delete from user where id=?";
         statement = connection.prepareStatement(sql);
         Long id = user.getId();
         statement.setLong(1, id.longValue());
         statement.executeUpdate();
      } catch (SQLException e)
      {
         throw new RuntimeException(e);
      } finally
      {
         close(statement, connection);
      }
   }

public User findByAccessKey(String accessKey) {
	 ResultSet rs = null;
     PreparedStatement statement = null;
     Connection connection = null;
     try
     {
        connection = getConnection();
        String sql = "select * from user where accesskey=?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, accessKey);
        rs = statement.executeQuery();
        if (!rs.next())
        {
           return null;
        }
        return read(rs);
     }
     catch (SQLException e)
     {
        throw new RuntimeException(e);
     }
     finally
     {
        close(rs, statement, connection);
     }
}
}