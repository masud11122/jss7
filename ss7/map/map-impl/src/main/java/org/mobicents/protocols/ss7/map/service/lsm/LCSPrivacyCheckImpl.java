/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.map.service.lsm;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSPrivacyCheck;
import org.mobicents.protocols.ss7.map.api.service.lsm.PrivacyCheckRelatedAction;
import org.mobicents.protocols.ss7.map.primitives.MAPAsnPrimitive;

/**
 * @author amit bhayani
 * 
 */
public class LCSPrivacyCheckImpl implements LCSPrivacyCheck, MAPAsnPrimitive {

	private static final int _TAG_CALL_SESSION_UNRELATED = 0;
	private static final int _TAG_CALL_SESSION_RELATED = 1;

	private PrivacyCheckRelatedAction callSessionUnrelated = null;
	private PrivacyCheckRelatedAction callSessionRelated = null;

	/**
	 * 
	 */
	public LCSPrivacyCheckImpl() {
		super();
	}

	/**
	 * @param callSessionUnrelated
	 * @param callSessionRelated
	 */
	public LCSPrivacyCheckImpl(PrivacyCheckRelatedAction callSessionUnrelated, PrivacyCheckRelatedAction callSessionRelated) {
		super();
		this.callSessionUnrelated = callSessionUnrelated;
		this.callSessionRelated = callSessionRelated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.LCSPrivacyCheck#
	 * getCallSessionUnrelated()
	 */
	@Override
	public PrivacyCheckRelatedAction getCallSessionUnrelated() {
		return this.callSessionUnrelated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.LCSPrivacyCheck#
	 * getCallSessionRelated()
	 */
	@Override
	public PrivacyCheckRelatedAction getCallSessionRelated() {
		return this.callSessionRelated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getTag()
	 */
	@Override
	public int getTag() throws MAPException {
		return Tag.SEQUENCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getTagClass
	 * ()
	 */
	@Override
	public int getTagClass() {
		return Tag.CLASS_UNIVERSAL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getIsPrimitive
	 * ()
	 */
	@Override
	public boolean getIsPrimitive() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#decodeAll
	 * (org.mobicents.protocols.asn.AsnInputStream)
	 */
	@Override
	public void decodeAll(AsnInputStream ansIS) throws MAPParsingComponentException {
		try {
			int length = ansIS.readLength();
			this._decode(ansIS, length);
		} catch (IOException e) {
			throw new MAPParsingComponentException("IOException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		} catch (AsnException e) {
			throw new MAPParsingComponentException("AsnException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#decodeData
	 * (org.mobicents.protocols.asn.AsnInputStream, int)
	 */
	@Override
	public void decodeData(AsnInputStream ansIS, int length) throws MAPParsingComponentException {
		try {
			this._decode(ansIS, length);
		} catch (IOException e) {
			throw new MAPParsingComponentException("IOException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		} catch (AsnException e) {
			throw new MAPParsingComponentException("AsnException when decoding SM_RP_DA: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		}
	}

	private void _decode(AsnInputStream asnIS, int length) throws MAPParsingComponentException, IOException, AsnException {

		AsnInputStream ais = asnIS.readSequenceStreamData(length);

		int tag = ais.readTag();

		// Decode callSessionUnrelated [0] PrivacyCheckRelatedAction
		if (ais.getTagClass() != Tag.CLASS_CONTEXT_SPECIFIC || !ais.isTagPrimitive() || tag != _TAG_CALL_SESSION_UNRELATED) {
			throw new MAPParsingComponentException(
					"Error while decoding LCSPrivacyCheck: Parameter 0 [callSessionUnrelated [0] PrivacyCheckRelatedAction] bad tag class, tag or not primitive",
					MAPParsingComponentExceptionReason.MistypedParameter);
		}

		int length1 = ais.readLength();
		int action = (int) ais.readIntegerData(length1);
		this.callSessionUnrelated = PrivacyCheckRelatedAction.getPrivacyCheckRelatedAction(action);

		while (true) {
			if (ais.available() == 0)
				break;

			tag = ais.readTag();
			switch (tag) {
			case _TAG_CALL_SESSION_RELATED:
				if (ais.getTagClass() != Tag.CLASS_CONTEXT_SPECIFIC || !ais.isTagPrimitive()) {
					throw new MAPParsingComponentException(
							"Error while decoding LCSPrivacyCheck: Parameter 1 [callSessionRelated [1] PrivacyCheckRelatedAction] bad tag class, tag or not primitive",
							MAPParsingComponentExceptionReason.MistypedParameter);
				}
				length1 = ais.readLength();
				action = (int) ais.readIntegerData(length1);
				this.callSessionRelated = PrivacyCheckRelatedAction.getPrivacyCheckRelatedAction(action);
				break;
			default:
				// Do we care?
				ais.advanceElement();
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeAll
	 * (org.mobicents.protocols.asn.AsnOutputStream)
	 */
	@Override
	public void encodeAll(AsnOutputStream asnOs) throws MAPException {
		this.encodeAll(asnOs, Tag.CLASS_UNIVERSAL, Tag.SEQUENCE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeAll
	 * (org.mobicents.protocols.asn.AsnOutputStream, int, int)
	 */
	@Override
	public void encodeAll(AsnOutputStream asnOs, int tagClass, int tag) throws MAPException {
		try {
			asnOs.writeTag(tagClass, false, tag);
			int pos = asnOs.StartContentDefiniteLength();
			this.encodeData(asnOs);
			asnOs.FinalizeContent(pos);
		} catch (AsnException e) {
			throw new MAPException("AsnException when encoding InformServiceCentreRequest: " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeData
	 * (org.mobicents.protocols.asn.AsnOutputStream)
	 */
	@Override
	public void encodeData(AsnOutputStream asnOs) throws MAPException {
		if (this.callSessionUnrelated == null) {
			throw new MAPException("Error while encoding LCSPrivacyCheck the mandatory parameter callSessionUnrelated is not defined");
		}

		try {
			asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _TAG_CALL_SESSION_UNRELATED, this.callSessionUnrelated.getAction());
		} catch (IOException e) {
			throw new MAPException("IOException when encoding parameter callSessionUnrelated", e);
		} catch (AsnException e) {
			throw new MAPException("AsnException when encoding parameter callSessionUnrelated", e);
		}

		if (this.callSessionRelated != null) {
			try {
				asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _TAG_CALL_SESSION_RELATED, this.callSessionRelated.getAction());
			} catch (IOException e) {
				throw new MAPException("IOException when encoding parameter callSessionRelated", e);
			} catch (AsnException e) {
				throw new MAPException("AsnException when encoding parameter callSessionRelated", e);
			}
		}
	}

}