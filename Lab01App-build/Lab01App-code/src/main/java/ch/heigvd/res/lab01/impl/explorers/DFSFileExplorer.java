package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;
import java.util.*;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

    @Override
    public void explore(File rootDirectory, IFileVisitor visitor) 
    {
        visitor.visit(rootDirectory);
        if (rootDirectory.isDirectory())
        {
            int i = 0;
            /* As the files in the list returned by the listFiles method are not
             * guaranted to be sorted, we need to sort them by their alphabetic
             * order in order for the test to work.
             */
            String[] names = new String[rootDirectory.listFiles().length];
            for(File f : rootDirectory.listFiles())
            {
                names[i++] = f.getPath();
            }
            Arrays.sort(names);
            
            for (String child : names)
            {
                File file = new File(child);
                if (file.isFile())
                {
                    visitor.visit(file);
                }
            }

            for (String child : names)
            {
                File file = new File(child);
                if (file.isDirectory())
                {
                    explore(file, visitor);
                }
            }
        }
    }
}
