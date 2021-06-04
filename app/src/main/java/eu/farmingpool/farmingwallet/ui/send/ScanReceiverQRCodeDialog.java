package eu.farmingpool.farmingwallet.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.logging.Event;
import eu.farmingpool.farmingwallet.logging.Log;
import eu.farmingpool.farmingwallet.logging.Tag;

import static eu.farmingpool.farmingwallet.logging.Log.logEvent;

public class ScanReceiverQRCodeDialog extends BottomSheetDialogFragment {
    private final Interface scanQRCodeDialogInterface;
    private CodeScanner codeScanner;

    public ScanReceiverQRCodeDialog(Interface scanQRCodeDialogInterface) {
        this.scanQRCodeDialogInterface = scanQRCodeDialogInterface;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_scan_receiver_qr_code, container, false);


        setupQRCodeScanner(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();

        super.onPause();
    }

    private void setupQRCodeScanner(View root) {
        CodeScannerView codeScannerView = root.findViewById(R.id.dialog_scan_receiver_qr_code_scanner);
        codeScanner = new CodeScanner(requireContext(), codeScannerView);

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);

        codeScanner.setDecodeCallback(result -> {
            Event event = new Event("onQRCodeScanned", result.getText(), Tag.EVENT_ACTIVITY_SEND);
            logEvent(Log.V, event);

            validateQrCode(result.getText());
        });

        codeScanner.setErrorCallback(result -> {
            Event event = new Event("onQRCodeScannedError", result.getMessage(), Tag.EVENT_ACTIVITY_SEND);
            logEvent(Log.V, event);
        });
    }

    private void validateQrCode(String result) {

    }

    public interface Interface {
        void onQrCodeScanned(Key key);
    }
}
