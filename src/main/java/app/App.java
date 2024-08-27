package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the 
 * Javalin HTTP Server and our web application.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class App {

    public static final int         JAVALIN_PORT    = 7000;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7000
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // All webpages are listed here as GET pages
        app.get(PageIndex.URL, new PageIndex());
        app.get(ViewTimes.URL, new ViewTimes());
        app.get(AdvancedTimes.URL, new AdvancedTimes());
        app.get(AddTimes.URL, new AddTimes());
        app.get(Runners.URL, new Runners());
        app.get(Intermediate.URL, new Intermediate());
        app.get(RunDisplay.URL, new RunDisplay());
        app.get(RunDisplayUser.URL, new RunDisplayUser());
        app.get(AddRunner.URL, new AddRunner());
        app.get(UserDisplay.URL, new UserDisplay());

        // Add / uncomment POST commands for any pages that need web form POSTS
        // app.post(PageIndex.URL, new PageIndex());
        // app.post(PageST2A.URL, new PageST2A());
        // app.post(PageST2B.URL, new PageST2B());
        app.post(AddTimes.URL, new AddTimes());
        // app.post(PageST3B.URL, new PageST3B());
        app.post(Intermediate.URL, new Intermediate());
        app.post(IntermediateUser.URL, new IntermediateUser());
        app.post(AddRunner.URL, new AddRunner());
    }

}
