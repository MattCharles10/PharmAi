package com.pharmai.features.prescriptions.data

import com.google.gson.Gson
import com.pharmai.features.prescriptions.data.local.PrescriptionDao
import com.pharmai.features.prescriptions.data.local.PrescriptionEntity
import com.pharmai.features.prescriptions.domain.models.Prescription
import com.pharmai.features.prescriptions.domain.repository.PrescriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrescriptionRepositoryImpl @Inject constructor(
    private val dao: PrescriptionDao,
    private val gson: Gson
) : PrescriptionRepository {

    override fun observeAll(): Flow<List<Prescription>> =
        dao.observeAll().map { it.map { e -> e.toDomain(gson) } }

    override suspend fun getById(id: Long): Prescription? =
        dao.getById(id)?.toDomain(gson)

    override suspend fun add(prescription: Prescription): Long =
        dao.insert(fromDomain(prescription, gson))

    override suspend fun update(prescription: Prescription) =
        dao.update(fromDomain(prescription, gson))

    override suspend fun delete(id: Long) = dao.delete(id)

    private fun PrescriptionEntity.toDomain(gson: Gson) = Prescription(
        id = id, doctorName = doctorName, hospitalName = hospitalName,
        prescriptionDate = prescriptionDate, expiryDate = expiryDate,
        drugName = drugName, dosage = dosage, frequency = frequency,
        duration = duration, instructions = instructions,
        imageUrls = gson.fromJson(imageUrls, Array<String>::class.java).toList(),
        isActive = isActive
    )

    // Moved OUT of companion object
    private fun fromDomain(prescription: Prescription, gson: Gson) = PrescriptionEntity(
        id = prescription.id, doctorName = prescription.doctorName,
        hospitalName = prescription.hospitalName, prescriptionDate = prescription.prescriptionDate,
        expiryDate = prescription.expiryDate, drugName = prescription.drugName,
        dosage = prescription.dosage, frequency = prescription.frequency,
        duration = prescription.duration, instructions = prescription.instructions,
        imageUrls = gson.toJson(prescription.imageUrls), isActive = prescription.isActive
    )
}