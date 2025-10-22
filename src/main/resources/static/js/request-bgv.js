(function () {
  const form = document.getElementById('requestForm');
  const submitBtn = document.getElementById('submitBtn');
  const msg = document.getElementById('reqMessage');
  const partnerEmailInput = document.getElementById('partnerEmail');
  const partnerEmailError = document.getElementById('partnerEmailError');

  function validateEmail(value) {
    // Simple RFC 5322-ish check
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  }

  function setStatus(text, ok) {
    msg.style.display = 'block';
    msg.classList.remove('success', 'fail');
    msg.classList.add(ok ? 'success' : 'fail');
    msg.textContent = text;
  }

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    msg.style.display = 'none';
    partnerEmailError.textContent = '';

    const payload = {
      targetEmail: partnerEmailInput.value.trim(),
      targetName: document.getElementById('partnerName').value.trim() || undefined,
      requesterEmail: document.getElementById('yourEmail').value.trim() || undefined,
      message: document.getElementById('message').value.trim() || undefined
    };

    // Validate required email
    if (!payload.targetEmail || !validateEmail(payload.targetEmail)) {
      partnerEmailError.textContent = 'Please enter a valid email address.';
      partnerEmailInput.focus();
      return;
    }

    submitBtn.disabled = true;

    try {
      const res = await fetch('/api/public/requests', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      let data = {};
      try { data = await res.json(); } catch (_) {}

      if (res.ok) {
        setStatus(data?.message || 'Request recorded. We’ve emailed your partner to review & consent.', true);
        form.reset();
      } else {
        setStatus(data?.message || 'Failed to create request.', false);
      }
    } catch (err) {
      setStatus('Network error. Please try again.', false);
    } finally {
      submitBtn.disabled = false;
    }
  });
})();
