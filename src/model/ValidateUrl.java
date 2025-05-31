package model;

import java.util.regex.Pattern;

public class ValidateUrl {

    private static final Pattern GITHUB_PATTERN = Pattern.compile(
            "^https?://(www\\.)?github\\.com/[^/]+/[^/]+/?$");

    private static final Pattern CLOUD_PATTERN = Pattern.compile(
            "^https?://(www\\.)?(drive\\.google\\.com|onedrive\\.live\\.com|dropbox\\.com|.+\\.sharepoint\\.com)/.+$");

    /**
     * Checks whether the URL is a valid GitHub repository URL.
     * @param url the URL to validate
     * @return true if valid GitHub URL
     */
    public static boolean isValidGitHubUrl(String url) {
        return GITHUB_PATTERN.matcher(url).matches();
    }

    /**
     * Checks whether the URL is a valid cloud document URL (Drive, OneDrive,
     * Dropbox, SharePoint).
     * @param url the URL to validate
     * @return true if valid cloud document URL
     */
    public static boolean isValidCloudUrl(String url) {
        return CLOUD_PATTERN.matcher(url).matches();
    }
}
