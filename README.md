# vidniceWrapper
Unofficial vidnice wrapper for Java

## Usage

### Get user info

```java
VidniceWrapper vidniceWrapper = new VidniceWrapper();
UserInfo userInfo = vidniceWrapper.getUserInfo(USER_ID);
```

### Get user's video list

```java
VidniceWrapper vidniceWrapper = new VidniceWrapper();
UserOwnVideos userOwnVideos = vidniceWrapper.getUserOwnVideos(USER_ID);
```