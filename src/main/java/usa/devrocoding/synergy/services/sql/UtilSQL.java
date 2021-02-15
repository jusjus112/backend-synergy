package usa.devrocoding.synergy.services.sql;

import com.google.common.io.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class UtilSQL {

  public static InputStream convertUniqueId(UUID uuid) {
    byte[] bytes = new byte[16];
    ByteBuffer.wrap(bytes)
        .putLong(uuid.getMostSignificantBits())
        .putLong(uuid.getLeastSignificantBits());
    return new ByteArrayInputStream(bytes);
  }

  public static UUID convertBinaryStream(InputStream stream) {
    ByteBuffer buffer = ByteBuffer.allocate(16);
    try {
      buffer.put(ByteStreams.toByteArray(stream));
      buffer.flip();
      return new UUID(buffer.getLong(), buffer.getLong());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
