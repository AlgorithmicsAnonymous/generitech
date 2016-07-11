package com.sandvoxel.generitech.common.integrations.top;

import com.google.common.base.Function;
import com.sandvoxel.generitech.common.util.LogHelper;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ITheOneProbe;

import javax.annotation.Nullable;

public class TopCompat implements Function<ITheOneProbe, Void> {
    public  static ITheOneProbe probe;

    public static int ELEMENT_SEQUENCER;

    @Nullable
    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        probe = theOneProbe;
        LogHelper.info("Enabled The One Probe Support");
        ELEMENT_SEQUENCER = probe.registerElementFactory(ElementSequencer::new);
        return null;
    }

    public static IProbeInfo addSequenceElement(IProbeInfo probeInfo, long bits, int current, boolean large) {
        return probeInfo.element(new ElementSequencer(bits, current, large));
    }
}
