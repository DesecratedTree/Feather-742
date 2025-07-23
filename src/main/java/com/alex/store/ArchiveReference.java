package com.alex.store;

import java.util.Arrays;

import com.alex.util.crc32.CRC32HGenerator;
import com.alex.util.whirlpool.Whirlpool;

public class ArchiveReference {

	private int nameHash;
	private byte[] whirlpool;
	private int crc;
	private int revision;
	private FileReference[] files;
	private int[] validFileIds;
	private boolean needsFilesSort;
	private boolean updatedRevision;

	public void updateRevision() {
		if (updatedRevision) {
			return;
		}
		revision++;
		updatedRevision = true;
	}

	public int getNameHash() {
		return nameHash;
	}

	public void setNameHash(int nameHash) {
		this.nameHash = nameHash;
	}

	public byte[] getWhirpool() {
		return whirlpool;
	}

	public void setWhirpool(byte[] whirpool) {
		this.whirlpool = whirpool;
	}

	public int getCRC() {
		return crc;
	}

	public void setCrc(int crc) {
		this.crc = crc;
	}

	public int getRevision() {
		return revision;
	}

	public FileReference[] getFiles() {
		return files;
	}

	public void setFiles(FileReference[] files) {
		this.files = files;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public int[] getValidFileIds() {
		return validFileIds;
	}

	public void setValidFileIds(int[] validFileIds) {
		this.validFileIds = validFileIds;
	}

	public boolean isNeedsFilesSort() {
		return needsFilesSort;
	}

	public void setNeedsFilesSort(boolean needsFilesSort) {
		this.needsFilesSort = needsFilesSort;
	}

	public void removeFileReference(int fileId) {
		int[] newValidFileIds = new int[validFileIds.length - 1];
		int count = 0;
		for (int id : validFileIds) {
			if (id == fileId) {
				continue;
			}
			newValidFileIds[count++] = id;
		}
		validFileIds = newValidFileIds;
		files[fileId] = null;
	}

	public void addEmptyFileReference(int fileId) {
		needsFilesSort = true;
		int[] newValidFileIds = Arrays.copyOf(validFileIds, validFileIds.length + 1);
		newValidFileIds[newValidFileIds.length - 1] = fileId;
		validFileIds = newValidFileIds;
		if (files.length <= fileId) {
			FileReference[] newFiles = Arrays.copyOf(files, fileId + 1);
			newFiles[fileId] = new FileReference();
			files = newFiles;
		} else {
			files[fileId] = new FileReference();
		}
	}

	public void sortFiles() {
		Arrays.sort(validFileIds);
		needsFilesSort = false;
	}

	public void reset() {
		whirlpool = null;
		updatedRevision = true;
		revision = 0;
		nameHash = 0;
		crc = 0;
		files = new FileReference[0];
		validFileIds = new int[0];
		needsFilesSort = false;
	}

	public void copyHeader(ArchiveReference fromReference, byte[] data, boolean generateCheksum) {
		if (generateCheksum) {
			setCrc(CRC32HGenerator.getCrc(data, 0, data.length - 2));
			setWhirpool(Whirlpool.getHash(data, 0, data.length - 2));
		} else {
			setCrc(fromReference.getCRC());
			setWhirpool(fromReference.getWhirpool());
		}
		setNameHash(fromReference.getNameHash());
		int[] validFiles = fromReference.getValidFileIds();
		setValidFileIds(Arrays.copyOf(validFiles, validFiles.length));
		FileReference[] files = fromReference.getFiles();
		setFiles(Arrays.copyOf(files, files.length));
	}

	public void updateHeader(byte[] data, int name, Integer[] fileNames) {
		crc = CRC32HGenerator.getCrc(data, 0, data.length - 2);
		whirlpool = Whirlpool.getHash(data, 0, data.length - 2);
		nameHash = name;
		files = new FileReference[fileNames.length];
		validFileIds = new int[fileNames.length];
		int count = 0;
		for (int index = 0; index < fileNames.length; index++) {
			if (fileNames[index] != null) {
				(files[index] = new FileReference()).setNameHash(fileNames[index]);
				validFileIds[count++] = index;
			}
		}
		validFileIds = Arrays.copyOf(validFileIds, count);
	}
}
