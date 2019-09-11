package Launcher.minecraft;

public class AuthInfos
{
    private String username;

    private String accessToken;

    private String clientToken;

    private String uuid;

    public AuthInfos(String username, String accessToken, String uuid)
    {
        this.username = username;
        this.accessToken = accessToken;
        this.uuid = uuid;
    }

    public AuthInfos(String username, String accessToken, String clientToken, String uuid)
    {
        this.username = username;
        this.accessToken = accessToken;
        this.clientToken = clientToken;
        this.uuid = uuid;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public String getClientToken()
    {
        return clientToken;
    }

    public String getUuid()
    {
        return this.uuid;
    }
}
