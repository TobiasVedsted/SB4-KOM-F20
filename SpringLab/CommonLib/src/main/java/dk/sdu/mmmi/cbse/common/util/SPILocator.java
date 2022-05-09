package dk.sdu.mmmi.cbse.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SPILocator {

    @SuppressWarnings("rawtypes")
    private static final Map<Class, ServiceLoader> loadermap = new HashMap<Class, ServiceLoader>();

    private SPILocator() {
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> locateAll(Class<T> service) {
        ServiceLoader<T> loader = loadermap.get(service);
        //ArrayList<T> loader = loadermap.get(service);

        boolean printStatement = false;

        if (loader == null) {
            //ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
            
            loader = ServiceLoader.load(service);
            loadermap.put(service, loader);
            //System.out.println("PUT   " +);
            printStatement = true;
        }

        List<T> list = new ArrayList<T>();

        if (loader != null) {
            try {
                for (T instance : loader) {
                    //System.out.println(instance.getClass());
                    list.add(instance);
                }
            } catch (ServiceConfigurationError serviceError) {
                serviceError.printStackTrace();
            }
        }

        if (printStatement) {
            System.out.println("Found " + list.size() + " implementations for interface: " + service.getName());
            /**
             * for (T i : list) { System.out.println(i.getClass()); }
             *
             */
        }

        return list;
    }
}
