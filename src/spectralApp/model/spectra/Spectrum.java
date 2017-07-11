package spectralApp.model.spectra;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Tim on 19.06.2017.
 */
public interface Spectrum {

    Map<Integer, Integer> spectra = new TreeMap<>();

    public Map<Integer, Integer>  showSpectrum();


}
