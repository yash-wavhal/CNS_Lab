package dhcp;

public class RemoteSoftwareInstaller {

    public static void main(String[] args) {
        String host = "192.168.1.100";
        String user = "admin";
        String password = "password123";
        String command = "sudo apt-get install -y curl";

        try {
            // Set up JSch session
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);

            // Skip host key checking
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Open an execution channel
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            // Capture command output
            InputStream in = channel.getInputStream();
            channel.connect();

            // Read command output
            byte[] tmp = new byte[1024];
            while (in.read(tmp) != -1) {
                System.out.print(new String(tmp));
            }

            // Close channel and session
            channel.disconnect();
            session.disconnect();
            System.out.println("Software installed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
